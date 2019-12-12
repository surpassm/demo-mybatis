package com.liaoin.demo.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.entity.user.*;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.mapper.user.*;
import com.liaoin.demo.service.user.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
public class GroupServiceImpl implements GroupService {
	@Resource
	private GroupMapper groupMapper;
	@Resource
	private GroupRoleMapper groupRoleMapper;
	@Resource
	private UserGroupMapper userGroupMapper;
	@Resource
	private MenuMapper menuMapper;
	@Resource
	private RoleMapper roleMapper;

	@Override
	public Result insert(Long userId, Group group) {
		if (group == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		//效验名称是否重复
		Group build = Group.builder().name(group.getName()).build();
		build.setIsDelete(0);
		int groupCount = groupMapper.selectCount(build);
		if (groupCount != 0) {
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		//查看父级是否存在
		if (isEnableParent(group)) {
			return fail(ResultCode.PARAM_TYPE_BIND_ERROR.getMsg());
		}
		group.setIsDelete(0);
		groupMapper.insert(group);
		return ok();
	}

	@Override
	public Result update(Long userId, Group group) {
		if (group == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		if (group.getIsDelete() == 1){
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}

		Example.Builder builder = new Example.Builder(Group.class);
		builder.where(WeekendSqls.<Group>custom().andEqualTo(Group::getIsDelete, 0));
		builder.where(WeekendSqls.<Group>custom().andEqualTo(Group::getName, group.getName()));
		builder.where(WeekendSqls.<Group>custom().andNotIn(Group::getId, Collections.singletonList(group.getId())));

		List<Group> selectCount = groupMapper.selectByExample(builder.build());
		if (selectCount.size() != 0) {
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		if (isEnableParent(group)) {
			return fail(ResultCode.PARAM_TYPE_BIND_ERROR.getMsg());
		}


		groupMapper.updateByPrimaryKeySelective(group);
		return ok();
	}

	private boolean isEnableParent(Group group) {
		if (group.getParentId() != null) {
			Group buildGroup = Group.builder().id(group.getParentId()).build();
			buildGroup.setIsDelete(0);
			int buildGroupCount = groupMapper.selectCount(buildGroup);
			return buildGroupCount == 0;
		}
		return false;
	}

	@Override
	public Result deleteGetById(Long userId, Long id) {
		if (id == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		Group group = groupMapper.selectByPrimaryKey(id);
		if (group == null) {
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		Group groupBuild = Group.builder().parentId(id).build();
		groupBuild.setIsDelete(0);
		int groupCount = groupMapper.selectCount(groupBuild);
		if (groupCount != 0){
			return fail("存在下级关联数据无法删除");
		}
		//组角色查询
		GroupRole groupRole = GroupRole.builder().groupId(id).build();
		groupRole.setIsDelete(0);
		int groupRoleCount = groupRoleMapper.selectCount(groupRole);
		//用户组查询
		UserGroup userGroup = UserGroup.builder().groupId(id).build();
		userGroup.setIsDelete(0);
		int userGroupCount = userGroupMapper.selectCount(userGroup);
		group.setIsDelete(1);
		groupMapper.updateByPrimaryKeySelective(group);
		return ok();
	}


	@Override
	public Result findById(Long userId, Long id) {
		if (id == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		Group group = groupMapper.selectByPrimaryKey(id);
		if (group == null) {
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		return ok(group);

	}

	@Override
	public Result pageQuery(Long userId, Integer page, Integer size, String sort, Group group) {
		page = null == page ? 1 : page;
		size = null == size ? 10 : size;
		PageHelper.startPage(page, size);
		Example.Builder builder = new Example.Builder(Group.class);
		builder.where(WeekendSqls.<Group>custom().andEqualTo(Group::getIsDelete, 0));
		if (group != null) {
			if (group.getId() != null) {
				builder.where(WeekendSqls.<Group>custom().andEqualTo(Group::getId, group.getId()));
			}
			if (group.getDescribes() != null && !"".equals(group.getDescribes().trim())) {
				builder.where(WeekendSqls.<Group>custom().andLike(Group::getDescribes, "%" + group.getDescribes() + "%"));
			}
			if (group.getName() != null && !"".equals(group.getName().trim())) {
				builder.where(WeekendSqls.<Group>custom().andLike(Group::getName, "%" + group.getName() + "%"));
			}
			if (group.getParentId() != null) {
				builder.where(WeekendSqls.<Group>custom().andEqualTo(Group::getParentId, group.getParentId()));
			} else {
				builder.where(WeekendSqls.<Group>custom().andIsNull(Group::getParentId));
			}
		} else {
			builder.where(WeekendSqls.<Group>custom().andIsNull(Group::getParentId));
		}
		Page<Group> all = (Page<Group>) groupMapper.selectByExample(builder.build());
		return ok(all.toPageInfo());
	}

	@Override
	public Result getParentId(Long userId, Long parentId) {
		List<Group> groups = groupMapper.selectChildByParentId(parentId);
		return ok(groups);
	}

	@Override
	public Result findByOnlyAndChildren(Long userId, Long id) {
		List<Group> groups = groupMapper.selectSelfAndChildByParentId(id);
		return ok(groups);
	}


	@Override
	public Result setGroupByRole(Long userId, Long id, String roleIds) {
		String[] splits = StringUtils.split(roleIds,",");
		if (splits == null || splits.length == 0){
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		int groupCount = groupMapper.selectCount(Group.builder().id(id).isDelete(0).build());
		if (groupCount == 0){
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		//删除原有组对应的角色
		Example.Builder builder = new Example.Builder(GroupRole.class);
		builder.where(WeekendSqls.<GroupRole>custom().andEqualTo(GroupRole::getIsDelete, 0));
		builder.where(WeekendSqls.<GroupRole>custom().andEqualTo(GroupRole::getGroupId, id));
		groupRoleMapper.deleteByExample(builder.build());
		//新增现有的角色
		List<GroupRole> groupRoleList = new ArrayList<>();
		for(String split : splits){
			groupRoleList.add(GroupRole.builder().groupId(id).roleId(Long.valueOf(split)).build());
		}
		if (groupRoleList.size() > 0) {
			groupRoleMapper.insertList(groupRoleList);
		}else {
			throw new CustomException("没有数据可以新增") ;
		}
		return ok();
	}


	@Override
	public Result findGroupToRole(Long userId, Long groupId, Integer page, Integer size, String sort) {
		List<Long> select = groupRoleMapper.select(GroupRole.builder().groupId(groupId).isDelete(0).build()).stream().map(GroupRole::getRoleId).collect(Collectors.toList());
		if (select.size() == 0){
			return ok(new Page<>());
		}
		page = null == page ? 1 : page;
		size = null == size ? 10 : size;
		PageHelper.startPage(page, size);
		Example.Builder builder = new Example.Builder(Role.class);
		builder.where(WeekendSqls.<Role>custom().andEqualTo(Role::getIsDelete, 0));
		builder.where(WeekendSqls.<Role>custom().andIn(Role::getId, select));
		Page<Role> all = (Page<Role>) roleMapper.selectByExample(builder.build());
		return ok(all.toPageInfo());
	}
}

