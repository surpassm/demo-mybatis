package com.liaoin.demo.service.user;

import com.liaoin.demo.common.Result;
import com.liaoin.demo.entity.user.UserInfo;

/**
  * @author mc
  * Create date 2019-03-14 20:41:03
  * Version 1.0
  * Description 接口
  */
public interface UserInfoService {
    /**
	 * 新增
	 * @param userInfo 对象
	 * @return 前端返回格式
	 */
    Result insert(Long userId, UserInfo userInfo);
    /**
	 * 修改
	 * @param userInfo 对象
	 * @return 前端返回格式
	 */
    Result update(Long userId, UserInfo userInfo);
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
	 * @param userInfo 查询条件
	 * @return 前端返回格式
	 */
    Result pageQuery(Long userId, Integer page, Integer size, String sort, UserInfo userInfo);

	/**
	 * 根据主键查询用户及角色、权限列表
	 * @param accessToken
	 * @param id
	 * @return
	 */
	Result findRolesAndMenus(Long userId, Long id);

	/**
	 * 设置用户、组
	 * @param accessToken
	 * @param id
	 * @param groupIds
	 * @return
	 */
	Result setUserByGroup(Long userId, Long id, String groupIds);

	/**
	 * 设置用户权限
	 * @param accessToken
	 * @param id
	 * @param menuIds
	 * @return
	 */
	Result setUserByMenu(Long userId, Long id, String menuIds);

	/**
	 * 设置用户、角色
	 * @param accessToken
	 * @param id
	 * @param roleIds
	 * @return
	 */
	Result setUserByRoles(Long userId, Long id, String roleIds);
}
