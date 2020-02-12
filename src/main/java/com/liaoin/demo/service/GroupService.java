package com.liaoin.demo.service;

import com.liaoin.demo.common.Result;
import com.liaoin.demo.domain.GroupDTO;
import com.liaoin.demo.domain.GroupVO;
import com.liaoin.demo.entity.Group;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 权限接口
  */
public interface GroupService {
    /**
	 * 新增
	 *
	 * @param group 对象
	 * @return 前端返回格式
	 */
	Group insert(Group group);

    /**
	 * 修改
	 *
	 * @param group 对象
	 * @return 前端返回格式
	 */
    void update(Group group);

    /**
	 * 根据主键删除
	 *
	 * @param id 标识
	 * @return 前端返回格式
	 */
	void deleteById(@NotNull @Min(1) Long id);

    /**
	 * 根据主键查询
	 *
	 * @param id 标识
	 * @return 前端返回格式
	 */
	Optional<Group> findById(@NotNull @Min(1) Long id);

    /**
	 * 条件分页查询
	 *
	 * @param page 当前页
	 * @param size 显示多少条
	 * @param sort 排序字段
	 * @param vo 查询条件
	 * @return 前端返回格式
	 */
    Result pageQuery(Integer page, Integer size, String sort, GroupVO vo);


	/**
	* 新增修改
	*
	* @param vo vo
	* @return Banner
	*/
	Group insertOrUpdate(GroupVO vo);

	List<GroupDTO> findAllParent();

	List<GroupDTO> findAllChild(Long parentId);

	/**
	 * 添加组部门
	 * @param groupId
	 * @param departmentId
	 */
	void addGroupDepartment(Long groupId, Long departmentId);

	/**
	 * 删除组部门
	 * @param groupId
	 * @param departmentId
	 */
	void deleteGroupDepartment(Long groupId, Long departmentId);

	/**
	 * 根据组ID分页查询部门
	 * @param page
	 * @param size
	 * @param sort
	 * @param groupId
	 * @return
	 */
	Result pageQueryDepartment(Integer page, Integer size, String sort, Long groupId);

	/**
	 * 添加组菜单
	 * @param groupId
	 * @param menuId
	 */
	void addGroupMenu(Long groupId, Long menuId);

	/**
	 * 删除组菜單
	 * @param groupId
	 * @param menuId
	 */
	void deleteGroupMenu(Long groupId, Long menuId);

	/**
	 * 根据组ID分页查询菜单
	 * @param page
	 * @param size
	 * @param sort
	 * @param groupId
	 * @return
	 */
	Result pageQueryMenu(Integer page, Integer size, String sort, Long groupId);

	/**
	 * 添加组角色
	 * @param groupId
	 * @param roleId
	 */
	void addGroupRole(Long groupId, Long roleId);

	/**
	 * 添加组角色
	 * @param groupId
	 * @param roleId
	 */
	void deleteGroupRole(Long groupId, Long roleId);

	/**
	 * 根据组ID分页查询角色
	 * @param page
	 * @param size
	 * @param sort
	 * @param groupId
	 * @return
	 */
	Result pageQueryRole(Integer page, Integer size, String sort, Long groupId);
}
