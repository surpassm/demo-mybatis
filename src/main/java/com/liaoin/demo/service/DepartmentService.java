package com.liaoin.demo.service;

import com.liaoin.demo.common.R;
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
     */
    void update(Department department);

    /**
     * 根据主键删除
     *
     * @param id 标识
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
     * @param vo   查询条件
     * @return 前端返回格式
     */
    R pageQuery(Integer page, Integer size, String sort, DepartmentVO vo);


    /**
     * 新增修改
     *
     * @param vo vo
     * @return Banner
     */
    Department insertOrUpdate(DepartmentVO vo);

    /**
     * 查询所有父级
     *
     * @return DepartmentDTO
     */
    List<DepartmentDTO> findAllParent();

    /**
     * 根据父级ID查询所有子级
     *
     * @param parentId 父级ID
     * @return DepartmentDTO
     */
    List<DepartmentDTO> findAllChild(Long parentId);

    /**
     * 查看数据是否存在
     *
     * @param department department
     * @return boolean
     */
    boolean selectCount(Department department);

    /**
     * 添加部门人员
     *
     * @param departmentId 部门id
     * @param userInfoId   用户id
     */
    void addDepartmentPerson(Long departmentId, Long userInfoId);


    /**
     * 刪除部门人员
     *
     * @param departmentId 部门id
     * @param userInfoId   用户id
     */
    void deleteDepartmentPerson(Long departmentId, Long userInfoId);

    /**
     * 分页查询部门下员工
     *
     * @param departmentId 部门id
     * @param page         当前页
     * @param size         大小
     * @param sort         排序
     * @return result
     */
    R pageQueryDepartmentPerson(Long departmentId, Integer page, Integer size, String sort);

    /**
     * 根据父级ID查询所有子级
     *
     * @param departmentId 部门id
     * @return Department
     */
    List<Department> findByParentId(Long departmentId);
}
