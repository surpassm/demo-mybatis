package com.liaoin.demo.service;

import com.liaoin.demo.common.R;
import com.liaoin.demo.common.Token;
import com.liaoin.demo.domain.MenuDTO;
import com.liaoin.demo.domain.UserInfoVO;
import com.liaoin.demo.entity.Operations;
import com.liaoin.demo.entity.Role;
import com.liaoin.demo.entity.UserInfo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @author mc
 * Create date 2020-02-10 10:15:21
 * Version 1.0
 * Description 用户接口
 */
public interface UserInfoService {
    /**
     * 新增
     *
     * @param userInfo 对象
     * @return 前端返回格式
     */
    UserInfo insert(UserInfo userInfo);

    /**
     * 修改
     *
     * @param userInfo 对象
     */
    void update(UserInfo userInfo);

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
    Optional<UserInfo> findById(@NotNull @Min(1) Long id);

    /**
     * 条件分页查询
     *
     * @param page 当前页
     * @param size 显示多少条
     * @param sort 排序字段
     * @param vo   查询条件
     * @return 前端返回格式
     */
    R pageQuery(Integer page, Integer size, String sort, UserInfoVO vo);


    /**
     * 新增
     *
     * @param vo vo
     * @return Banner
     */
    UserInfo insertOrUpdate(UserInfoVO vo);


    /**
     * 创建超级管理员
     *
     * @return UserInfo
     */
    UserInfo createSupperAdmin();


    /**
     * 账户密码登录
     *
     * @param username 账号
     * @param password 密码
     * @return token
     */
    Token login(String username, String password);

    /**
     * 小程序登陆
     *
     * @param mobile 手机号码
     * @return token
     */
    Token smallLogin(String mobile);

    /**
     * 添加用户组
     *
     * @param userInfoId 用户标识
     * @param groupId    组标识
     */
    void addUserGroup(Long userInfoId, Long groupId);

    /**
     * 删除用户组
     *
     * @param userInfoId 用户标识
     * @param groupId    组标识
     */
    void deleteUserGroup(Long userInfoId, Long groupId);

    /**
     * 根据用户ID分页查询组
     *
     * @param page       页
     * @param size       大小
     * @param sort       排序字段
     * @param userInfoId 用户标识
     * @return result
     */
    R pageQueryGroup(Integer page, Integer size, String sort, Long userInfoId);

    /**
     * 添加用户角色
     *
     * @param userInfoId 用户标识
     * @param roleId     角色标识
     */
    void addUserRole(Long userInfoId, Long roleId);

    /**
     * 删除用户角色
     *
     * @param userInfoId 用户标识
     * @param roleId     角色标识
     */
    void deleteUserRole(Long userInfoId, Long roleId);

    /**
     * 根据用户ID分页查询角色
     *
     * @param page       页
     * @param size       大小
     * @param sort       排序字段
     * @param userInfoId 用户标识
     * @return result
     */
    R pageQueryRole(Integer page, Integer size, String sort, Long userInfoId);

    /**
     * 根据用户标识获取菜单
     *
     * @param userId 用户标识
     * @return MenuDTO
     */
    List<MenuDTO> selectUserMenu(Long userId);

    /**
     * 根据用户标识获取接口
     *
     * @param userId 用户标识
     * @return Operations
     */
    List<Operations> selectUserOperations(Long userId);

    /**
     * 查询账号是否存在
     *
     * @param username username
     * @return boolean
     */
    boolean getUsername(String username);




    /**
     * 是否启用禁用
     *
     * @param id
     * @param isEnable
     */
    void isEnable(Long userId, Long id, Integer isEnable);

    void resetPassword(Long userId, Long id);

    /**
     * 查询当前登录人授予用户的角色列表
     *
     * @param loginUserId 当前登录人
     * @param userInfoId  用户
     * @return dto
     */
    List<Role> selectBindRole(@NotNull Long loginUserId, @NotNull Long userInfoId);
}
