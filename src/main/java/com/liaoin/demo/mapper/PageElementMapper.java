package com.liaoin.demo.mapper;

import com.liaoin.demo.entity.PageElement;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 页面元素持久层
  */
public interface PageElementMapper extends Mapper<PageElement>, MySqlMapper<PageElement> {


}
