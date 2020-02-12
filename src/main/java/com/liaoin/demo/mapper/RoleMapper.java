package com.liaoin.demo.mapper;

import com.liaoin.demo.entity.Department;
import com.liaoin.demo.entity.Power;
import com.liaoin.demo.entity.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
  * @author mc
  * Create date 2020-02-10 10:15:21
  * Version 1.0
  * Description 角色持久层
  */
public interface RoleMapper extends Mapper<Role>, MySqlMapper<Role> {

	/**
	 * 根据角色ID查询部门
	 * @param roleId
	 * @return
	 */
	List<Department> findDepartmentByRoleId(@Param("roleId") Long roleId);

	/**
	 * 根据角色ID查询权限
	 * @param roleId
	 * @return
	 */
	List<Power> findPowerByRoleId(@Param("roleId") Long roleId);
}
