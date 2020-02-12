package com.liaoin.demo.mapper;

import com.liaoin.demo.domain.DepartmentDTO;
import com.liaoin.demo.entity.Department;
import com.liaoin.demo.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 部门持久层
  */
public interface DepartmentMapper extends Mapper<Department>, MySqlMapper<Department> {


	List<DepartmentDTO> findAllParent();

	List<DepartmentDTO> findAllChild(@Param("parentId") Long parentId);

	List<UserInfo> pageQueryDepartmentPerson(@Param("departmentId") Long departmentId);
}
