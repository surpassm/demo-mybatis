package com.liaoin.demo.service.user;

import com.liaoin.demo.common.Result;
import com.liaoin.demo.entity.user.Role;

/**
  * @author mc
  * Create date 2019-03-14 20:41:03
  * Version 1.0
  * Description 接口
  */
public interface RoleService {
    /**
	 * 新增
	 * @param role 对象
	 * @return 前端返回格式
	 */
    Result insert(Long userId, Role role);
    /**
	 * 修改
	 * @param role 对象
	 * @return 前端返回格式
	 */
    Result update(Long userId, Role role);
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
	 * @param role 查询条件
	 * @return 前端返回格式
	 */
    Result pageQuery(Long userId, Integer page, Integer size, String sort, Role role);

	/**
	 * 根据主键查询角色权限列表
	 * @param id
	 * @return
	 */
	Result findMenus(Long userId, Long id);

	/**
	 * 设置角色权限
	 * @param id
	 * @param menuId
	 * @return
	 */
	Result setRoleByMenu(Long userId, Long id, String menuId);
}
