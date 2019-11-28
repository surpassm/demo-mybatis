package com.liaoin.demo.mapper.user;

import com.liaoin.demo.entity.user.Operations;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
  * @author mc
  * Create date 2019-11-28 10:47:51
  * Version 1.0
  * Description 后台功能接口持久层
  */
public interface OperationsMapper extends Mapper<Operations>, MySqlMapper<Operations> {


}