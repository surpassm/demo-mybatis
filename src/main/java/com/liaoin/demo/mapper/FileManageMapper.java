package com.liaoin.demo.mapper;

import com.liaoin.demo.entity.FileManage;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
  * @author mc
  * Create date 2019-04-01 10:47:03
  * Version 1.0
  * Description 文件管理持久层
  */
@Mapper
public interface FileManageMapper extends tk.mybatis.mapper.common.Mapper<FileManage>, MySqlMapper<FileManage> {


}
