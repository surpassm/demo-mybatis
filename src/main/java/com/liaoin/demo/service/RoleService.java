package com.liaoin.demo.service;

import com.liaoin.demo.common.R;
import com.liaoin.demo.domain.RoleVO;
import com.liaoin.demo.entity.Role;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @author mc
 * Create date 2020-02-10 10:15:21
 * Version 1.0
 * Description 角色接口
 */
public interface RoleService {
    /**
     * 新增
     *
     * @param role 对象
     * @return 前端返回格式
     */
    Role insert(Role role);

    /**
     * 修改
     *
     * @param role 对象
     */
    void update(Role role);

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
    Optional<Role> findById(@NotNull @Min(1) Long id);

    /**
     * 条件分页查询
     *
     * @param page 当前页
     * @param size 显示多少条
     * @param sort 排序字段
     * @param vo   查询条件
     * @return 前端返回格式
     */
    R pageQuery(Integer page, Integer size, String sort, RoleVO vo);


    /**
     * 新增修改
     *
     * @param vo vo
     * @return Banner
     */
    Role insertOrUpdate(RoleVO vo);

    /**
     * 添加角色部门
     *
     * @param roleId       角色标识
     * @param departmentId 部门标识
     */
    void addRoleDepartment(Long roleId, Long departmentId);

    /**
     * 删除角色部门
     *
     * @param roleId       角色标识
     * @param departmentId 部门标识
     */
    void deleteRoleDepartment(Long roleId, Long departmentId);

    /**
     * 根据角色ID分页查询部门
     *
     * @param page   当前页
     * @param size   大小
     * @param sort   排序
     * @param roleId 角色标识
     * @return Result
     */
    R pageQueryDepartment(Integer page, Integer size, String sort, Long roleId);

    /**
     * 添加角色权限
     *
     * @param roleId  角色标识
     * @param powerId 权限标识
     */
    void addRolePower(Long roleId, Long powerId);

    /**
     * 删除角色权限
     *
     * @param roleId  角色标识
     * @param powerId 权限标识
     */
    void deleteRolePower(Long roleId, Long powerId);

    /**
     * 根据角色ID分页查询权限
     *
     * @param page   当前页
     * @param size   大小
     * @param sort   排序
     * @param roleId 角色标识
     * @return Result
     */
    R pageQueryPower(Integer page, Integer size, String sort, Long roleId);


    /**
     * 查询所有角色信息
     * @return
     */
    List<Role> findAll();
}
