package com.liaoin.demo.service;

import com.liaoin.demo.common.Result;
import com.liaoin.demo.domain.DepartmentDTO;
import com.liaoin.demo.domain.DepartmentVO;
import com.liaoin.demo.entity.Department;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 部门接口
  */
public interface DepartmentService {
    /**
	 * 新增
	 *
	 * @param department 对象
	 * @return 前端返回格式
	 */
	Department insert(Department department);

    /**
	 * 修改
	 *
	 * @param department 对象
	 * @return 前端返回格式
	 */
    void update(Department department);

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
	Optional<Department> findById(@NotNull @Min(1) Long id);

    /**
	 * 条件分页查询
	 *
	 * @param page 当前页
	 * @param size 显示多少条
	 * @param sort 排序字段
	 * @param vo 查询条件
	 * @return 前端返回格式
	 */
    Result pageQuery(Integer page, Integer size, String sort, DepartmentVO vo);


	/**
	* 新增修改
	*
	* @param vo vo
	* @return Banner
	*/
	Department insertOrUpdate(DepartmentVO vo);


	List<DepartmentDTO> findAllParent();

	List<DepartmentDTO> findAllChild(Long parentId);

	boolean selectCount(Department department);

	/**
	 * 添加部门人员
	 * @param departmentId
	 * @param userInfoId
	 */
	void addDepartmentPerson(Long departmentId, Long userInfoId);


	/**
	 * 刪除部门人员
	 * @param departmentId
	 * @param userInfoId
	 */
	void deleteDepartmentPerson(Long departmentId, Long userInfoId);

	/**
	 * 分页查询部门下员工
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 */
	Result pageQueryDepartmentPerson(Long departmentId, Integer page, Integer size, String sort);

	/**
	 * 根据父级ID查询所有子级
	 * @param departmentId
	 * @return
	 */
	List<Department> findByParentId(Long departmentId);
}
