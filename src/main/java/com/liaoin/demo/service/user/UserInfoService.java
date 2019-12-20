package com.liaoin.demo.service.user;

import com.liaoin.demo.common.Result;
import com.liaoin.demo.domain.user.UserInfoDTO;
import com.liaoin.demo.entity.user.UserInfo;

/**
  * @author mc
  * Create date 2019-03-14 20:41:03
  * Version 1.0
  * Description 接口
  */
public interface UserInfoService {
    /**
	 * 根据主键删除
	 * @param id 标识
	 * @return 前端返回格式
	 */
    Result deleteGetById(Long userId, Long id);
    /**
	 * 根据主键查询
	 * @param id 标识
	 * @return 前端返回格式
	 */
    Result findById(Long userId, Long id);
    /**
	 * 条件分页查询
	 * @param page 当前页
	 * @param size 显示多少条
	 * @param sort 排序字段
	 * @param userInfo 查询条件
	 * @return 前端返回格式
	 */
    Result pageQuery(Long userId, Integer page, Integer size, String sort, UserInfo userInfo);

	/**
	 * 根据主键查询用户及角色、权限列表
	 * @param userId
	 * @param id
	 * @return
	 */
	Result findRolesAndMenus(Long userId, Long id);

	/**
	 * 设置用户、组
	 * @param userId
	 * @param id
	 * @param groupIds
	 * @return
	 */
	Result setUserByGroup(Long userId, Long id, String groupIds);

	/**
	 * 设置用户权限
	 * @param userId
	 * @param id
	 * @param menuIds
	 * @return
	 */
	Result setUserByMenu(Long userId, Long id, String menuIds);

	/**
	 * 设置用户、角色
	 * @param userId
	 * @param id
	 * @param roleIds
	 * @return
	 */
	Result setUserByRoles(Long userId, Long id, String roleIds);

    /**
     * 创建超级管理员
     * @return
     */
    Result createAdmin();

	/**
	 * 登录
	 * @return
	 */
	Result loginIn(String code);

	/**
	 * 设置用户基本信息
	 * @param userId 用户标识
	 * @param sessionKey 微信会话key
	 * @param signature 使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息
	 * @param rawData 不包括敏感信息的原始数据字符串，用于计算签名
	 * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
	 * @param iv 加密算法的初始向量
	 * @return
	 */
	Result setInfo(Long userId, String sessionKey, String signature, String rawData, String encryptedData, String iv);
}
