package com.liaoin.demo.mapper;

import com.liaoin.demo.domain.GroupDTO;
import com.liaoin.demo.entity.Department;
import com.liaoin.demo.entity.Group;
import com.liaoin.demo.entity.Menu;
import com.liaoin.demo.entity.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 权限持久层
  */
public interface GroupMapper extends Mapper<Group>, MySqlMapper<Group> {


	List<GroupDTO> findAllParent();

	List<GroupDTO> findAllChild(@Param("parentId") Long parentId);

	List<Department> findDepartmentByGroupId(@Param("groupId") Long groupId);

	List<Menu> findMenuByGroupId(@Param("groupId") Long groupId);

	List<Role> findRoleByGroupId(@Param("groupId") Long groupId);
}
