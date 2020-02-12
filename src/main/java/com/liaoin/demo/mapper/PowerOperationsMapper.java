package com.liaoin.demo.mapper;

import com.liaoin.demo.entity.PowerOperations;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
  * @author mc
  * Create date 2020-02-10 10:15:19
  * Version 1.0
  * Description 权限与API接口关联表持久层
  */
public interface PowerOperationsMapper extends Mapper<PowerOperations>, MySqlMapper<PowerOperations> {


}
