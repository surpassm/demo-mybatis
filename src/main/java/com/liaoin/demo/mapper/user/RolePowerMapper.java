package com.liaoin.demo.mapper.user;

import com.liaoin.demo.entity.user.RolePower;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
  * @author mc
  * Create date 2019-11-28 10:47:51
  * Version 1.0
  * Description 权限与角色关联表持久层
  */
public interface RolePowerMapper extends Mapper<RolePower>, MySqlMapper<RolePower> {


}
