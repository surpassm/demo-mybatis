package com.liaoin.demo.mapper;

import com.liaoin.demo.entity.UserGroups;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 用户组持久层
  */
public interface UserGroupsMapper extends Mapper<UserGroups>, MySqlMapper<UserGroups> {


}