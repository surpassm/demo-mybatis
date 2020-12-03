package com.liaoin.demo.service.impl;

import com.github.pagehelper.Page;
import com.liaoin.demo.common.R;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.MenuDTO;
import com.liaoin.demo.domain.MenuVO;
import com.liaoin.demo.domain.MenuVos;
import com.liaoin.demo.entity.Menu;
import com.liaoin.demo.entity.PowerMenu;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.mapper.MenuMapper;
import com.liaoin.demo.mapper.PowerMenuMapper;
import com.liaoin.demo.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.liaoin.demo.common.R.ok;


/**
 * @author mc
 * Create date 2020-02-10 10:15:20
 * Version 1.0
 * Description 权限实现类
 */
@Slf4j
@Service
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private PowerMenuMapper powerMenuMapper;



    @Override
    public Menu insert(Menu menu) {
        menuMapper.insert(menu);
        return menu;
    }

    @Override
    public void update(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Menu> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        Menu menu = byId.get();
        menu.setIsDelete(1);
        this.update(menu);
    }


    @Override
    public Optional<Menu> findById(Long id) {
        return Optional.ofNullable(menuMapper.selectByPrimaryKey(id));

    }

    @Override
    public R pageQuery(Integer page, Integer size, String sort, MenuVO menuVO) {
        super.pageQuery(page, size, sort);
        Example.Builder builder = new Example.Builder(Menu.class);
        builder.where(WeekendSqls.<Menu>custom().andEqualTo(Menu::getIsDelete, 0));
        if (menuVO != null) {
            if (menuVO.getDescribes() != null) {
                builder.where(WeekendSqls.<Menu>custom().andLike(Menu::getDescribes, "%" + menuVO.getDescribes() + "%"));
            }
            if (menuVO.getName() != null) {
                builder.where(WeekendSqls.<Menu>custom().andLike(Menu::getName, "%" + menuVO.getName() + "%"));
            }
            if (menuVO.getType() != null) {
                builder.where(WeekendSqls.<Menu>custom().andEqualTo(Menu::getType, menuVO.getType()));
            }
        }
        builder.where(WeekendSqls.<Menu>custom().andIsNull(Menu::getParentId));
        Page<Menu> all = (Page<Menu>) menuMapper.selectByExample(builder.build());
        return ok(all.getTotal(), all.getResult());
    }

    @Override
    public Menu insertVO(List<MenuVos> vos) {
        return null;
    }

    @Override
    public void insertList(List<MenuVos> vos) {
        List<Menu> menus = menuMapper.selectAll();
        if (menus.size() > 0) {
            throw new CustomException(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(), ResultCode.INTERFACE_INNER_INVOKE_ERROR.getMsg());
        }
        menuVosFor(vos, null);
    }

    /**
     * 递归批量新增
     *
     * @param vos      集合
     * @param parentId 父级
     */
    public void menuVosFor(List<MenuVos> vos, Long parentId) {
        for (MenuVos vo : vos) {
            Menu build = Menu.builder().parentId(parentId).isDelete(0).name(vo.getName()).path(vo.getPath()).type(vo.getType()).build();
            this.insert(build);
            if (vo.getChildren() != null && vo.getChildren().size() != 0) {
                menuVosFor(vo.getChildren(), build.getId());
            }
        }
    }

    @Override
    public Menu updateVO(MenuVO vo) {
        Menu menu = vo.convertTo();
        if (menu.getId() == null) {
            throw new CustomException(ResultCode.PARAM_IS_INVALID.getCode(), ResultCode.PARAM_IS_INVALID.getMsg());
        }
        //父级效验
        Long parentId = menu.getParentId();
        if (parentId != null) {
            if (!findById(parentId).isPresent()) {
                throw new CustomException(ResultCode.RESULT_DATA_NONE.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
            }
        }
        this.update(menu);
        return menu;
    }

    /**
     * 查询所有父级
     *
     * @return MenuDTO
     */
    @Override
    public List<MenuDTO> findAllParent() {
        return menuMapper.findAllParent();
    }

    /**
     * 根据父级ID查询所有子级
     *
     * @param parentId parentId
     * @return MenuDTO
     */
    @Override
    public List<MenuDTO> findAllChild(Long parentId) {
        return menuMapper.findAllChild(parentId);
    }

    @Override
    public List<Menu> findByParentId(Long menuId) {
        List<Menu> select = menuMapper.select(Menu.builder().parentId(menuId).isDelete(0).build());
        if (select.size() > 0) {
            for (Menu menu : select) {
                List<Menu> byParentId = findByParentId(menu.getId());
                if (byParentId.size() > 0) {
                    select.addAll(byParentId);
                }
            }
        }
        return select;
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param userId userId
     * @return MenuDTO
     */
    @Override
    public List<MenuDTO> findByUserId(Long userId) {
        List<MenuDTO> menus = menuMapper.findByUserId(userId);
        List<MenuDTO> result = new ArrayList<>();
        recursionFindByUserId(menus,result);

        return result;
    }

    /**
     * 递归返回
     */
    public void recursionFindByUserId(List<MenuDTO> menus,List<MenuDTO> result){
        for (MenuDTO menu : menus) {
            if (menu.getChildren().size() > 0){
                recursionFindByUserId(menu.getChildren(),result);
            }else {
                result.add(menu);
            }

        }
    }


    @Override
    public List<MenuDTO> findAll(Long powerId) {
        List<Long> menuIds = powerMenuMapper.select(PowerMenu.builder().powerId(powerId).build()).stream().map(PowerMenu::getMenuId).collect(Collectors.toList());
        List<MenuDTO> menus = menuMapper.findAll();
        recursion(menus,menuIds);
        return menus;
    }

    /**
     * 递归处理数据
     */
    public void recursion(List<MenuDTO> menus,List<Long> menuIds){
        for (MenuDTO menu : menus) {
            if (menuIds != null && menuIds.size() > 0 ) {
                for (Long menuId : menuIds) {
                    if (menu.getId().equals(menuId)) {
                        menu.setIsCheck(true);
                    }
                    if (menu.getIsCheck() == null || !menu.getIsCheck()){
                        menu.setIsCheck(false);
                    }
                }
            }else{
                menu.setIsCheck(false);
            }
            if (menu.getChildren().size() > 0){
                recursion(menu.getChildren(),menuIds);
            }
        }
    }
}

