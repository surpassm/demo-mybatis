package com.liaoin.demo.mapper;

import com.liaoin.demo.entity.Groups;
import com.liaoin.demo.entity.Role;
import com.liaoin.demo.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
  * @author mc
  * Create date 2020-02-10 10:15:21
  * Version 1.0
  * Description 用户持久层
  */
public interface UserInfoMapper extends Mapper<UserInfo>, MySqlMapper<UserInfo> {


	List<Role> findRoleByUserId(@Param("userInfoId") Long userInfoId);

	List<Groups> findGroupByUserId(@Param("userInfoId") Long userInfoId);
}
