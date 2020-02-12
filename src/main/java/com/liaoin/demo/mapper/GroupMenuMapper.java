package com.liaoin.demo.mapper;

import com.liaoin.demo.entity.GroupMenu;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
  * @author mc
  * Create date 2020-02-10 10:15:19
  * Version 1.0
  * Description 组权限持久层
  */
public interface GroupMenuMapper extends Mapper<GroupMenu>, MySqlMapper<GroupMenu> {


}
