package com.liaoin.demo.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.entity.user.*;
import com.liaoin.demo.mapper.user.MenuMapper;
import com.liaoin.demo.service.user.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.util.*;

import static com.liaoin.demo.common.Result.fail;
import static com.liaoin.demo.common.Result.ok;


/**
 * @author mc
 * Create date 2019-03-14 20:41:03
 * Version 1.0
 * Description 权限实现类
 */
@Slf4j
@Service
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
public class MenuServiceImpl implements MenuService {
	@Resource
	private MenuMapper menuMapper;


	@Override
	public Result insert(Long userId, Menu menu) {
		if (menu == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		//效验名称是否重复
		Menu build = Menu.builder().name(menu.getName()).build();
		build.setIsDelete(0);
		int groupCount = menuMapper.selectCount(build);
		if (groupCount != 0) {
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		//查看父级是否存在
		if (isEnableParent(menu)) {
			return fail(ResultCode.PARAM_TYPE_BIND_ERROR.getMsg());
		}

		menu.setIsDelete(0);
		menuMapper.insert(menu);
		return ok();
	}

	@Override
	public Result update(Long userId, Menu menu) {
		if (menu == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		if (menu.getIsDelete() == 1){
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}

		Example.Builder builder = new Example.Builder(Menu.class);
		builder.where(WeekendSqls.<Menu>custom().andEqualTo(Menu::getIsDelete, 0));
		builder.where(WeekendSqls.<Menu>custom().andEqualTo(Menu::getName, menu.getName()));
		builder.where(WeekendSqls.<Menu>custom().andNotIn(Menu::getId, Collections.singletonList(menu.getId())));

		List<Menu> selectCount = menuMapper.selectByExample(builder.build());
		if (selectCount.size() != 0) {
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		if (isEnableParent(menu)) {
			return fail(ResultCode.PARAM_TYPE_BIND_ERROR.getMsg());
		}


		menuMapper.updateByPrimaryKeySelective(menu);
		return ok();
	}

	private boolean isEnableParent(Menu menu) {
		if (menu.getParentId() != null) {
			Menu buildMenu = Menu.builder().id(menu.getParentId()).build();
			buildMenu.setIsDelete(0);
			int buildmenuMapperCount = menuMapper.selectCount(buildMenu);
			return buildmenuMapperCount == 0;
		}
		return false;
	}

	@Override
	public Result deleteGetById(Long userId, Long id) {
		if (id == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		Menu menu = menuMapper.selectByPrimaryKey(id);
		if (menu == null) {
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		Menu menuBuild = Menu.builder().parentId(id).build();
		menuBuild.setIsDelete(0);
		int menuCount = menuMapper.selectCount(menuBuild);
		if (menuCount != 0){
			return fail("存在下级关联数据无法删除");
		}
		//角色权限查询
		menu.setIsDelete(1);
		menuMapper.updateByPrimaryKeySelective(menu);
		return ok();
	}


	@Override
	public Result findById(Long userId, Long id) {
		if (id == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		Menu menu = menuMapper.selectByPrimaryKey(id);
		if (menu == null) {
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		return ok(menu);

	}

	@Override
	public Result pageQuery(Long userId, Integer page, Integer size, String sort, Menu menu) {
		page = null == page ? 1 : page;
		size = null == size ? 10 : size;
		PageHelper.startPage(page, size);
		Example.Builder builder = new Example.Builder(Menu.class);
		builder.where(WeekendSqls.<Menu>custom().andEqualTo(Menu::getIsDelete, 0));
		if (menu != null) {
			if (menu.getId() != null) {
				builder.where(WeekendSqls.<Menu>custom().andEqualTo(Menu::getId, menu.getId()));
			}
			if (menu.getDescribes() != null && !"".equals(menu.getDescribes().trim())) {
				builder.where(WeekendSqls.<Menu>custom().andLike(Menu::getDescribes, "%" + menu.getDescribes() + "%"));
			}
			if (menu.getMenuIcon() != null && !"".equals(menu.getMenuIcon().trim())) {
				builder.where(WeekendSqls.<Menu>custom().andLike(Menu::getMenuIcon, "%" + menu.getMenuIcon() + "%"));
			}
			if (menu.getMenuIndex() != null) {
				builder.where(WeekendSqls.<Menu>custom().andEqualTo(Menu::getMenuIndex, menu.getMenuIndex()));
			}
			if (menu.getMenuUrl() != null && !"".equals(menu.getMenuUrl().trim())) {
				builder.where(WeekendSqls.<Menu>custom().andLike(Menu::getMenuUrl, "%" + menu.getMenuUrl() + "%"));
			}
			if (menu.getName() != null && !"".equals(menu.getName().trim())) {
				builder.where(WeekendSqls.<Menu>custom().andLike(Menu::getName, "%" + menu.getName() + "%"));
			}
			if (menu.getParentId() != null) {
				builder.where(WeekendSqls.<Menu>custom().andEqualTo(Menu::getParentId, menu.getParentId()));
			}
			if (menu.getPath() != null && !"".equals(menu.getPath().trim())) {
				builder.where(WeekendSqls.<Menu>custom().andLike(Menu::getPath, "%" + menu.getPath() + "%"));
			}
			if (menu.getType() != null) {
				builder.where(WeekendSqls.<Menu>custom().andEqualTo(Menu::getType, menu.getType()));
			} else {
				builder.where(WeekendSqls.<Menu>custom().andIsNull(Menu::getParentId));
			}
		} else {
			builder.where(WeekendSqls.<Menu>custom().andIsNull(Menu::getParentId));
		}
		Page<Menu> all = (Page<Menu>) menuMapper.selectByExample(builder.build());
		return ok(all.toPageInfo());
	}

	@Override
	public Result getParentId(Long userId, Long parentId) {
		List<Menu> menus = menuMapper.selectChildByParentId(parentId);
		return ok(menus);
	}

	@Override
	public Result findByOnlyAndChildren(Long userId, Long id) {
		List<Menu> menus = menuMapper.selectSelfAndChildByParentId(id);
		return ok(menus);
	}

}

