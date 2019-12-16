package com.liaoin.demo.service.user;

import com.liaoin.demo.common.Result;
import com.liaoin.demo.domain.user.DepartmentDto;
import com.liaoin.demo.entity.user.Department;

/**
 * @author mc
 * Create date 2019-03-14 20:41:03
 * Version 1.0
 * Description 部门接口
 */
public interface DepartmentService {
    /**
     * 新增
     *
     * @return 前端返回格式
     */
    Result insert(Long userId, DepartmentDto dto);
    /**
     * 修改
     *
     * @param dto 对象
     * @return 前端返回格式
     */
    Result update(Long userId,DepartmentDto dto);

    /**
     * 根据主键删除
     *
     * @param id 标识
     * @return 前端返回格式
     */
    Result deleteGetById(Long userId, Long id);

    /**
     * 根据主键查询
     *
     * @param id 标识
     * @return 前端返回格式
     */
    Result findById(Long userId, Long id);

    /**
     * 条件分页查询
     *
     * @param page       当前页
     * @param size       显示多少条
     * @param sort       排序字段
     * @param department 查询条件
     * @return 前端返回格式
     */
    Result pageQuery(Long userId, Integer page, Integer size, String sort, Department department);

    /**
     * 根据父级Id查询
     *
     * @param parentId
     * @return 前端返回格式
     */
    Result getParentId(Long userId, Integer parentId);

    /**
     * 根据主键查询自己和所有子级
     *
     * @param id id
     * @return
     */
    Result findByOnlyAndChildren(Long userId, Integer id);

    /**
     * 根据部门查询所有员工
     *
     * @param id id
     * @return 前端返回格式
     */
    Result getDepartmentId(Long userId, Long id);

    /**
     * 根据名称查询数量
     *
     * @param departmentName
     * @return
     */
    int getDepartmentName(String departmentName);


}
