package com.liaoin.demo.service.user;

import com.liaoin.demo.common.Result;
import com.liaoin.demo.entity.user.Group;

/**
  * @author mc
  * Create date 2019-03-14 20:41:03
  * Version 1.0
  * Description 权限接口
  */
public interface GroupService {
    /**
	 * 新增
	 * @param group 对象
	 * @return 前端返回格式
	 */
    Result insert(Long userId, Group group);
    /**
	 * 修改
	 * @param group 对象
	 * @return 前端返回格式
	 */
    Result update(Long userId, Group group);
    /**
	 * 根据主键删除
	 * @param id 标识
	 * @return 前端返回格式
	 */
    Result deleteGetById(Long userId, Long id);
    /**
	 * 根据主键查询
	 * @param id 标识
	 * @return 前端返回格式
	 */
    Result findById(Long userId, Long id);
    /**
	 * 条件分页查询
	 * @param page 当前页
	 * @param size 显示多少条
	 * @param sort 排序字段
	 * @param group 查询条件
	 * @return 前端返回格式
	 */
    Result pageQuery(Long userId, Integer page, Integer size, String sort, Group group);

	/**
	 * 根据父级Id查询
	 * @param accessToken
	 * @param parentId
	 * @return
	 */
	Result getParentId(Long userId, Long parentId);

	/**
	 * 根据主键查询自己和所有子级
	 * @param accessToken
	 * @param id
	 * @return
	 */
	Result findByOnlyAndChildren(Long userId, Long id);

	/**
	 * 设置组的角色
	 * @param accessToken
	 * @param id
	 * @param roleIds
	 * @return
	 */
	Result setGroupByRole(Long userId, Long id, String roleIds);


	/**
	 * 查询组的角色
	 * @param accessToken
	 * @param groupId
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 */
	Result findGroupToRole(Long userId, Long groupId, Integer page, Integer size, String sort);
}
