package com.liaoin.demo.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.domain.user.UserInfoDto;
import com.liaoin.demo.entity.user.*;
import com.liaoin.demo.mapper.user.*;
import com.liaoin.demo.service.user.UserInfoService;
import com.liaoin.demo.util.JwtUtils;
import com.liaoin.demo.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static com.liaoin.demo.common.Result.fail;
import static com.liaoin.demo.common.Result.ok;


/**
 * @author mc
 * Create date 2019-03-14 20:41:03
 * Version 1.0
 * Description 实现类
 */
@Slf4j
@Service
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
public class UserInfoServiceImpl implements UserInfoService {
	@Resource
	private UserInfoMapper userInfoMapper;
	@Resource
	private UserGroupMapper userGroupMapper;
	@Resource
	private UserRoleMapper userRoleMapper;
	@Resource
	private DepartmentMapper departmentMapper;
	@Resource
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	@Override
	public Result insertOrUpdate(Long userId, UserInfoDto dto) {
	    //数据效验
		if (dto == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
        if (!ValidateUtil.isPassword(dto.getPassword())){
            return fail("匹配小写字母、大写字母、数字、特殊符号的两种及两种以上【非中文】");
        }
        if (!ValidateUtil.isRealName(dto.getUsername())){
            return fail("字母、中文、点组成2-20位");
        }
        if (dto.getName() != null){
            //效验手姓名
            if (!ValidateUtil.isRealName(dto.getName())){
                return fail("姓名格式错误");
            }
        }
        if (dto.getMobile() != null){
            if (!ValidateUtil.isMobilePhone(dto.getMobile())){
                return fail(ResultCode.PARAM_IS_INVALID.getMsg());
            }
        }
		//新增
        if (dto.getId() == null){
            //新增查询账号是否存在
            int count = userInfoMapper.selectCount(UserInfo.builder().username(dto.getUsername().trim()).isDelete(0).build());
            if (count != 0){
                return fail("账号已存在");
            }

        }else {//修改

        }

		return ok();
	}


	@Override
	public Result deleteGetById(Long userId, Long id) {
		if (id == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);
		if (userInfo == null) {
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		userInfo.setIsDelete(1);
		userInfoMapper.updateByPrimaryKeySelective(userInfo);
		return ok();
	}


	@Override
	public Result findById(Long userId, Long id) {
		if (id == null) {
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);
		if (userInfo == null) {
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		return ok(userInfo);

	}

	@Override
	public Result pageQuery(Long userId, Integer page, Integer size, String sort, UserInfo userInfo) {
		page = null == page ? 1 : page;
		size = null == size ? 10 : size;
		PageHelper.startPage(page, size);
		Example.Builder builder = new Example.Builder(UserInfo.class);
		builder.where(WeekendSqls.<UserInfo>custom().andEqualTo(UserInfo::getIsDelete, 0));
		if (userInfo != null) {
			if (userInfo.getId() != null) {
				builder.where(WeekendSqls.<UserInfo>custom().andEqualTo(UserInfo::getId, userInfo.getId()));
			}
			if (userInfo.getHeadUrl() != null && !"".equals(userInfo.getHeadUrl().trim())) {
				builder.where(WeekendSqls.<UserInfo>custom().andLike(UserInfo::getHeadUrl, "%" + userInfo.getHeadUrl() + "%"));
			}
			if (userInfo.getLandingTime() != null) {
				builder.where(WeekendSqls.<UserInfo>custom().andEqualTo(UserInfo::getLandingTime, userInfo.getLandingTime()));
			}
			if (userInfo.getMobile() != null && !"".equals(userInfo.getMobile().trim())) {
				builder.where(WeekendSqls.<UserInfo>custom().andLike(UserInfo::getMobile, "%" + userInfo.getMobile() + "%"));
			}
			if (userInfo.getName() != null && !"".equals(userInfo.getName().trim())) {
				builder.where(WeekendSqls.<UserInfo>custom().andLike(UserInfo::getName, "%" + userInfo.getName() + "%"));
			}
			if (userInfo.getPassword() != null && !"".equals(userInfo.getPassword().trim())) {
				builder.where(WeekendSqls.<UserInfo>custom().andLike(UserInfo::getPassword, "%" + userInfo.getPassword() + "%"));
			}
			if (userInfo.getUserInfoIndex() != null) {
				builder.where(WeekendSqls.<UserInfo>custom().andEqualTo(UserInfo::getUserInfoIndex, userInfo.getUserInfoIndex()));
			}
			if (userInfo.getUsername() != null && !"".equals(userInfo.getUsername().trim())) {
				builder.where(WeekendSqls.<UserInfo>custom().andLike(UserInfo::getUsername, "%" + userInfo.getUsername() + "%"));
			}
		}
		Page<UserInfo> all = (Page<UserInfo>) userInfoMapper.selectByExample(builder.build());
		return ok(all.toPageInfo());
	}

	/**
	 * 根据主键查询用户及角色、权限列表
	 *
	 * @param id 系统标识
	 * @return 返回数据
	 */
	@Override
	public Result findRolesAndMenus(Long userId, Long id) {
		UserInfo userInfo = userInfoMapper.selectByUserInfoAndRolesAndMenus(id);
		if (userInfo == null){
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		return ok(userInfo);
	}

	/**
	 * 设置用户、组
	 * @param id 用户系统标识
	 * @param groupIds 组系统标识
	 * @return 返回数据
	 */
	@Override
	public Result setUserByGroup(Long userId, Long id, String groupIds) {
		String[] splits = StringUtils.split(groupIds,",");
		if (splits == null || splits.length == 0){
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		int count = userInfoMapper.selectCount(UserInfo.builder().id(id).isDelete(0).build());
		if (count == 0){
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		//删除原有用户对应的组
		Example.Builder builder = new Example.Builder(UserGroup.class);
		builder.where(WeekendSqls.<UserGroup>custom().andEqualTo(UserGroup::getIsDelete, 0));
		builder.where(WeekendSqls.<UserGroup>custom().andEqualTo(UserGroup::getUserId, id));
		userGroupMapper.deleteByExample(builder.build());
		//新增现有的用户组
		for(String split: splits){
			UserGroup build = UserGroup.builder().userId(id).groupId(Long.valueOf(split)).build();
			build.setIsDelete(0);
			userGroupMapper.insert(build);
		}
		return ok();
	}

	/**
	 * 设置用户权限
	 * @param id 用户系统标识
	 * @param menuIds 组系统标识
	 * @return 返回数据
	 */
	@Override
	public Result setUserByMenu(Long userId, Long id, String menuIds) {
		String[] splits = StringUtils.split(menuIds,",");
		if (splits == null || splits.length == 0){
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		int count = userInfoMapper.selectCount(UserInfo.builder().id(id).isDelete(0).build());
		if (count == 0){
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		return ok();
	}

	/**
	 * 设置用户、角色
	 * @param id 用户系统标识
	 * @param roleIds 组系统标识
	 * @return 返回数据
	 */
	@Override
	public Result setUserByRoles(Long userId, Long id, String roleIds) {
		String[] splits = StringUtils.split(roleIds,",");
		if (splits == null || splits.length == 0){
			return fail(ResultCode.PARAM_IS_INVALID.getMsg());
		}
		int count = userInfoMapper.selectCount(UserInfo.builder().id(id).isDelete(0).build());
		if (count == 0){
			return fail(ResultCode.RESULE_DATA_NONE.getMsg());
		}
		//删除原有用户对应的角色
		Example.Builder builder = new Example.Builder(UserRole.class);
		builder.where(WeekendSqls.<UserRole>custom().andEqualTo(UserRole::getIsDelete, 0));
		builder.where(WeekendSqls.<UserRole>custom().andEqualTo(UserRole::getUserId, id));
		userRoleMapper.deleteByExample(builder.build());
		//新增现有的用户角色
		for(String split: splits){
			UserRole build = UserRole.builder().userId(id).roleId(Long.valueOf(split)).build();
			build.setIsDelete(0);
			userRoleMapper.insert(build);
		}
		return ok();
	}

    @Override
    public Result createAdmin() {
	    String username = "admin";
	    String password = "123456";
        UserInfo admin = userInfoMapper.selectOne(UserInfo.builder().isDelete(0).username(username).build());
        if (admin != null){
            admin.setPassword(password);
            return ok(admin);
        }
        String encode = bCryptPasswordEncoder.encode(password);
        UserInfo build = UserInfo.builder().username(username).isDelete(0).password(encode).build();
        userInfoMapper.insert(build);
        build.setPassword(password);
        return ok(build);
    }

    @Override
	public Result loginIn(String username, String password) {
		//查询用户账号是否存在
		UserInfo userInfo = userInfoMapper.selectOne(UserInfo.builder().isDelete(0).username(username).build());
		if (userInfo == null){
			return fail(ResultCode.USER_LOGIN_ERROR.getCode(),ResultCode.USER_LOGIN_ERROR.getMsg());
		}
		if (!bCryptPasswordEncoder.matches(password,userInfo.getPassword())){
			return fail(ResultCode.USER_LOGIN_ERROR.getCode(),ResultCode.USER_LOGIN_ERROR.getMsg());
		}
        String token = JwtUtils.getSubFromToken(userInfo.getId().toString());
        Map<String ,String > result = new HashMap<>(1);
        result.put("token",token);
		return ok(result);
	}
}

