package com.liaoin.demo.mapper;

import com.liaoin.demo.entity.Menu;
import com.liaoin.demo.entity.Operations;
import com.liaoin.demo.entity.Power;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 权限表持久层
  */
public interface PowerMapper extends Mapper<Power>, MySqlMapper<Power> {


	List<Menu> findMenuByPowerId(@Param("powerId") Long powerId);

	List<Operations> findOperationsByPowerId(@Param("powerId") Long powerId);

	/**
	 * 根据角色ID查询权限
	 * @param roleId
	 * @return
	 */
	List<Power> findPowerByRoleId(@Param("roleId") Long roleId);
}
