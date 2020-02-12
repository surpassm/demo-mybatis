package com.liaoin.demo.service.impl;

import com.github.pagehelper.Page;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.MenuDTO;
import com.liaoin.demo.domain.MenuVO;
import com.liaoin.demo.entity.Menu;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.mapper.MenuMapper;
import com.liaoin.demo.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.liaoin.demo.common.Result.ok;


/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 权限实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;

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
    public void deleteById(Long id){
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
    public Result pageQuery(Integer page, Integer size, String sort, MenuVO menuVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(Menu.class);
        builder.where(WeekendSqls.<Menu>custom().andEqualTo(Menu::getIsDelete, 0));
        if(menuVO != null){
        	if (menuVO.getDescribes() != null){
				builder.where(WeekendSqls.<Menu>custom().andLike(Menu::getDescribes, "%"+menuVO.getDescribes()+"%"));
			}
        	if (menuVO.getName() != null){
				builder.where(WeekendSqls.<Menu>custom().andLike(Menu::getName, "%"+menuVO.getName()+"%"));
			}
        	if (menuVO.getType() != null){
				builder.where(WeekendSqls.<Menu>custom().andEqualTo(Menu::getType, menuVO.getType()));
			}
        }
		builder.where(WeekendSqls.<Menu>custom().andIsNull(Menu::getParentId));
        Page<Menu> all = (Page<Menu>) menuMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }

    @Override
    public Menu insertVO(MenuVO vo) {
		Menu menu = vo.convertTo();
		if (menu.getId() != null){
			throw new CustomException(ResultCode.PARAM_IS_INVALID.getCode(),ResultCode.PARAM_IS_INVALID.getMsg());
		}
		//父级效验
		Long parentId = menu.getParentId();
		if (parentId != null){
			if (!findById(parentId).isPresent()){
				throw new CustomException(ResultCode.RESULT_DATA_NONE.getCode(),ResultCode.RESULT_DATA_NONE.getMsg());
			}
		}
		menu.setIsDelete(0);
		this.insert(menu);
		return menu;
    }

    @Override
    public Menu updateVO(MenuVO vo) {
		Menu menu = vo.convertTo();
		if (menu.getId() == null){
			throw new CustomException(ResultCode.PARAM_IS_INVALID.getCode(),ResultCode.PARAM_IS_INVALID.getMsg());
		}
		//父级效验
		Long parentId = menu.getParentId();
		if (parentId != null){
			if (!findById(parentId).isPresent()){
				throw new CustomException(ResultCode.RESULT_DATA_NONE.getCode(),ResultCode.RESULT_DATA_NONE.getMsg());
			}
		}
		this.update(menu);
		return menu;
    }
	/**
	 * 查询所有父级
	 * @return
	 */
	@Override
	public List<MenuDTO> findAllParent() {
		return menuMapper.findAllParent();
	}
	/**
	 * 根据父级ID查询所有子级
	 * @param parentId
	 * @return
	 */
	@Override
	public List<MenuDTO> findAllChild(Long parentId) {
		return menuMapper.findAllChild(parentId);
	}

	@Override
	public List<Menu> findByParentId(Long menuId) {
		List<Menu> select = menuMapper.select(Menu.builder().parentId(menuId).isDelete(0).build());
		if(select.size() > 0){
			for (Menu menu : select) {
				List<Menu> byParentId = findByParentId(menu.getId());
				if (byParentId.size() > 0){
					select.addAll(byParentId);
				}
			}
		}
		return select;
	}
}

