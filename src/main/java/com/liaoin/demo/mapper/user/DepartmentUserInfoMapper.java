package com.liaoin.demo.mapper.user;

import com.liaoin.demo.entity.user.DepartmentUserInfo;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
  * @author mc
  * Create date 2019-11-28 10:47:50
  * Version 1.0
  * Description 部门用户关系表持久层
  */
public interface DepartmentUserInfoMapper extends Mapper<DepartmentUserInfo>, MySqlMapper<DepartmentUserInfo> {


}
