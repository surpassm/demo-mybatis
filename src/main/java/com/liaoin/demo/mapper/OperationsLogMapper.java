package com.liaoin.demo.mapper;

import com.liaoin.demo.entity.OperationsLog;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * LogMapper
 *
 * @author zhangquanli
 */
public interface OperationsLogMapper extends Mapper<OperationsLog> , MySqlMapper<OperationsLog> {
}
