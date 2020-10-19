package com.liaoin.demo.service;

import com.liaoin.demo.common.R;
import com.liaoin.demo.domain.GroupsDTO;
import com.liaoin.demo.domain.GroupsVO;
import com.liaoin.demo.entity.Groups;

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
public interface GroupsService {
    /**
     * 新增
     *
     * @param groups 对象
     * @return 前端返回格式
     */
    Groups insert(Groups groups);

    /**
     * 修改
     *
     * @param groups 对象
     */
    void update(Groups groups);

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
    Optional<Groups> findById(@NotNull @Min(1) Long id);

    /**
     * 条件分页查询
     *
     * @param page 当前页
     * @param size 显示多少条
     * @param sort 排序字段
     * @param vo   查询条件
     * @return 前端返回格式
     */
    R pageQuery(Integer page, Integer size, String sort, GroupsVO vo);


    /**
     * 新增修改
     *
     * @param vo vo
     * @return Banner
     */
    Groups insertOrUpdate(GroupsVO vo);

    /**
     * 查询所有父级
     *
     * @return GroupsDTO
     */
    List<GroupsDTO> findAllParent();

    /**
     * 根据父级ID查询所有子级
     *
     * @param parentId parentId
     * @return GroupsDTO
     */
    List<GroupsDTO> findAllChild(Long parentId);

    /**
     * 添加组部门
     *
     * @param groupId      组标识
     * @param departmentId 部门标识
     */
    void addGroupDepartment(Long groupId, Long departmentId);

    /**
     * 删除组部门
     *
     * @param groupId      组标识
     * @param departmentId 部门标识
     */
    void deleteGroupDepartment(Long groupId, Long departmentId);

    /**
     * 根据组ID分页查询部门
     *
     * @param page    当前页
     * @param size    显示多少条
     * @param sort    排序字段
     * @param groupId 组主键
     * @return Result
     */
    R pageQueryDepartment(Integer page, Integer size, String sort, Long groupId);

    /**
     * 添加组菜单
     *
     * @param groupId 组标识
     * @param menuId  菜单标识
     */
    void addGroupMenu(Long groupId, Long menuId);

    /**
     * 删除组菜單
     *
     * @param groupId 组标识
     * @param menuId  菜单标识
     */
    void deleteGroupMenu(Long groupId, Long menuId);

    /**
     * 根据组ID分页查询菜单
     *
     * @param page    当前页
     * @param size    显示多少条
     * @param sort    排序字段
     * @param groupId 组主键
     * @return Result
     */
    R pageQueryMenu(Integer page, Integer size, String sort, Long groupId);

    /**
     * 添加组角色
     *
     * @param groupId 组标识
     * @param roleId  角色标识
     */
    void addGroupRole(Long groupId, Long roleId);

    /**
     * 添加组角色
     *
     * @param groupId 组系统标识
     * @param roleId  角色系统标识
     */
    void deleteGroupRole(Long groupId, Long roleId);

    /**
     * 根据组ID分页查询角色
     *
     * @param page    当前页
     * @param size    显示多少条
     * @param sort    排序字段
     * @param groupId 组主键
     * @return Result
     */
    R pageQueryRole(Integer page, Integer size, String sort, Long groupId);
}
