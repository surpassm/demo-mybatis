package com.liaoin.demo.service;

import com.liaoin.demo.common.Result;
import com.liaoin.demo.domain.PowerVO;
import com.liaoin.demo.entity.Power;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 权限表接口
  */
public interface PowerService {
    /**
	 * 新增
	 *
	 * @param power 对象
	 * @return 前端返回格式
	 */
	Power insert(Power power);

    /**
	 * 修改
	 *
	 * @param power 对象
	 * @return 前端返回格式
	 */
    void update(Power power);

    /**
	 * 根据主键删除
	 *
	 * @param id 标识
	 * @return 前端返回格式
	 */
	void deleteById(@NotNull @Min(1) Long id);

    /**
	 * 根据主键查询
	 *
	 * @param id 标识
	 * @return 前端返回格式
	 */
	Optional<Power> findById(@NotNull @Min(1) Long id);

    /**
	 * 条件分页查询
	 *
	 * @param page 当前页
	 * @param size 显示多少条
	 * @param sort 排序字段
	 * @param vo 查询条件
	 * @return 前端返回格式
	 */
    Result pageQuery(Integer page, Integer size, String sort, PowerVO vo);


	/**
	* 新增修改
	*
	* @param vo vo
	* @return Banner
	*/
	Power insertOrUpdate(PowerVO vo, Long userId);

	/**
	 * 添加权限菜单
	 * @param powerId
	 * @param menuId
	 */
	void addPowerMenu(Long powerId, Long menuId);

	/**
	 * 删除权限菜單
	 * @param powerId
	 * @param menuId
	 */
	void deletePowerMenu(Long powerId, Long menuId);

	/**
	 * 根据权限ID分页查询菜单
	 * @param page
	 * @param size
	 * @param sort
	 * @param powerId
	 * @return
	 */
	Result pageQueryMenu(Integer page, Integer size, String sort, Long powerId);

	/**
	 * 添加权限接口
	 * @param powerId
	 * @param operationsId
	 */
	void addPowerOperations(Long powerId, Long operationsId);

	/**
	 * 删除权限接口
	 * @param powerId
	 * @param operationsId
	 */
	void deletePowerOperations(Long powerId, Long operationsId);

	/**
	 * 根据权限ID分页查询接口
	 * @param page
	 * @param size
	 * @param sort
	 * @param powerId
	 * @return
	 */
	Result pageQueryOperations(Integer page, Integer size, String sort, Long powerId);
}
