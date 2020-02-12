package com.liaoin.demo.mapper;

import com.liaoin.demo.entity.DepartmentUserInfo;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
  * @author mc
  * Create date 2020-02-10 10:15:19
  * Version 1.0
  * Description 部门用户关系表持久层
  */
public interface DepartmentUserInfoMapper extends Mapper<DepartmentUserInfo>, MySqlMapper<DepartmentUserInfo> {


}
