package com.liaoin.demo.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.entity.user.Role;
import com.liaoin.demo.entity.user.UserInfo;
import com.liaoin.demo.mapper.user.RoleMapper;
import com.liaoin.demo.service.user.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * Description 实现类
 */
@Slf4j
@Service
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleMapper roleMapper;

	@Override
	public Result insert(Long userId, Role role) {
		if (role == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}

		Role build = Role.builder().name(role.getName()).build();
		build.setIsDelete(0);
		int selectCount = roleMapper.selectCount(build);
		if (selectCount != 0) {
			return fail(ResultCode.DATA_ALREADY_EXISTED.getMsg());
		}
		role.setIsDelete(0);
		roleMapper.insert(role);
		return ok();
	}

	@Override
	public Result update(Long userId, Role role) {
		if (role == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		if (role.getIsDelete() == 1) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}

		Example.Builder builder = new Example.Builder(Role.class);
		builder.where(WeekendSqls.<Role>custom().andEqualTo(Role::getIsDelete, 0));
		builder.where(WeekendSqls.<Role>custom().andEqualTo(Role::getName, role.getName()));
		builder.where(WeekendSqls.<Role>custom().andNotIn(Role::getId, Collections.singletonList(role.getId())));

		int selectCount = roleMapper.selectCountByExample(builder.build());
		if (selectCount != 0) {
			return fail(ResultCode.DATA_ALREADY_EXISTED.getMsg());
		}

		roleMapper.updateByPrimaryKeySelective(role);
		return ok();
	}

	@Override
	public Result deleteGetById(Long userId, Long id) {
		if (id == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		Role role = roleMapper.selectByPrimaryKey(id);
		if (role == null) {
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}


		role.setIsDelete(1);
		roleMapper.updateByPrimaryKeySelective(role);
		return ok();
	}


	@Override
	public Result findById(Long userId, Long id) {
		if (id == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		Role role = roleMapper.selectByPrimaryKey(id);
		if (role == null) {
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		return ok(role);

	}

	@Override
	public Result pageQuery(Long userId, Integer page, Integer size, String sort, Role role) {
		page = null == page ? 1 : page;
		size = null == size ? 10 : size;
		PageHelper.startPage(page, size);
		Example.Builder builder = new Example.Builder(Role.class);
		builder.where(WeekendSqls.<Role>custom().andEqualTo(Role::getIsDelete, 0));
		if (role != null) {
			if (role.getId() != null) {
				builder.where(WeekendSqls.<Role>custom().andEqualTo(Role::getId, role.getId()));
			}
			if (role.getDescribes() != null && !"".equals(role.getDescribes().trim())) {
				builder.where(WeekendSqls.<Role>custom().andLike(Role::getDescribes, "%" + role.getDescribes() + "%"));
			}
			if (role.getName() != null && !"".equals(role.getName().trim())) {
				builder.where(WeekendSqls.<Role>custom().andLike(Role::getName, "%" + role.getName() + "%"));
			}
			if (role.getRoleIndex() != null) {
				builder.where(WeekendSqls.<Role>custom().andEqualTo(Role::getRoleIndex, role.getRoleIndex()));
			}
			if (role.getSecurityRoles() != null && !"".equals(role.getSecurityRoles().trim())) {
				builder.where(WeekendSqls.<Role>custom().andLike(Role::getSecurityRoles, "%" + role.getSecurityRoles() + "%"));
			}
		}
		Page<Role> all = (Page<Role>) roleMapper.selectByExample(builder.build());
		return ok(all.toPageInfo());
	}

	@Override
	public Result findMenus(Long userId, Long id) {
		if (id == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		Role role = roleMapper.findByMenus(id);
		if (role == null) {
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		return ok(role);
	}

	@Override
	public Result setRoleByMenu(Long userId, Long id, String menuId) {
		String[] splits = StringUtils.split(menuId,",");
		if (splits == null || splits.length == 0){
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		Role role = Role.builder().id(id).build();
		role.setIsDelete(0);
		int roleCount = roleMapper.selectCount(role);
		if (roleCount == 0){
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}

		return ok();
	}
}

