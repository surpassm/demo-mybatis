package com.liaoin.demo.service;

import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.Token;
import com.liaoin.demo.domain.UserInfoVO;
import com.liaoin.demo.entity.UserInfo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
	 * @return 前端返回格式
	 */
    void update(UserInfo userInfo);

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
	Optional<UserInfo> findById(@NotNull @Min(1) Long id);

    /**
	 * 条件分页查询
	 *
	 * @param page 当前页
	 * @param size 显示多少条
	 * @param sort 排序字段
	 * @param vo 查询条件
	 * @return 前端返回格式
	 */
    Result pageQuery(Integer page, Integer size, String sort, UserInfoVO vo);


	/**
	* 新增
	*
	* @param vo vo
	* @return Banner
	*/
	UserInfo insertVO(UserInfoVO vo);

	/**
	* 修改
	*
	* @param vo vo
	* @return Banner
	*/
	UserInfo updateVO(UserInfoVO vo);

	/**
	 * 创建超级管理员
	 *
	 * @return
	 */
	UserInfo createSupperAdmin();


	/**
	 * 账户密码登录
	 * @param username 账号
	 * @param password 密码
	 * @return token
	 */
	Token login(String username, String password);

	/**
	 * 小程序登陆
	 * @param mobile 手机号码
	 * @return token
	 */
	Token smallLogin(String mobile);

	void addUserGroup(Long userInfoId, Long groupId);

	void deleteUserGroup(Long userInfoId, Long groupId);

	Result pageQueryGroup(Integer page, Integer size, String sort, Long userInfoId);

	void addUserRole(Long userInfoId, Long roleId);

	void deleteUserRole(Long userInfoId, Long roleId);

	Result pageQueryRole(Integer page, Integer size, String sort, Long userInfoId);
}
