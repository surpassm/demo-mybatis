package com.liaoin.demo.mapper.user;

import com.liaoin.demo.entity.user.PowerOperations;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
  * @author mc
  * Create date 2019-11-28 10:47:50
  * Version 1.0
  * Description 权限与API接口关联表持久层
  */
public interface PowerOperationsMapper extends Mapper<PowerOperations>, MySqlMapper<PowerOperations> {


}
