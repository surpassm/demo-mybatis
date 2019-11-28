package com.liaoin.demo.service.user.impl;

import com.liaoin.demo.entity.user.*;
import com.liaoin.demo.mapper.user.*;


/**
 * @author mc
 * Create date 2019/3/15 13:20
 * Version 1.0
 * Description
 */
public class CommonImpl {

	static void groupRoleDeleteUpdata(UserInfo loginUserInfo, GroupRole groupRole, int groupRoleCount, GroupRoleMapper groupRoleMapper) {
		if (groupRoleCount != 0){
			groupRole.setIsDelete(1);
			groupRoleMapper.updateByPrimaryKeySelective(groupRole);
		}
	}
	static void userGroupDeleteUpdata(UserInfo loginUserInfo, UserGroup userGroup, int userGroupCount, UserGroupMapper userGroupMapper) {
		if (userGroupCount != 0){
			userGroup.setIsDelete(1);
			userGroupMapper.updateByPrimaryKeySelective(userGroup);
		}
	}
}
