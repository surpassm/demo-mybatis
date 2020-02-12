package com.liaoin.demo.mapper;

import com.liaoin.demo.entity.PowerPageElement;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
  * @author mc
  * Create date 2020-02-10 10:15:19
  * Version 1.0
  * Description 权限与页面关联表持久层
  */
public interface PowerPageElementMapper extends Mapper<PowerPageElement>, MySqlMapper<PowerPageElement> {


}
