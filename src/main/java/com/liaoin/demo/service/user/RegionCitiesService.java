package com.liaoin.demo.service.user;

import com.liaoin.demo.common.Result;
import com.liaoin.demo.entity.user.RegionCities;

/**
  * @author mc
  * Create date 2019-11-28 10:47:51
  * Version 1.0
  * Description 城市信息表接口
  */
public interface RegionCitiesService {
    /**
	 * 新增
	 *
	 * @param userId userId
	 * @param regionCities 对象
	 * @return 前端返回格式
	 */
    Result insert(Long userId, RegionCities regionCities);
    /**
	 * 修改
	 *
	 * @param userId userId
	 * @param regionCities 对象
	 * @return 前端返回格式
	 */
    Result update(Long userId, RegionCities regionCities);
    /**
	 * 根据主键删除
	 *
	 * @param userId userId
	 * @param id 标识
	 * @return 前端返回格式
	 */
    Result deleteGetById(Long userId, Long id);
    /**
	 * 根据主键查询
	 *
	 * @param userId userId
	 * @param id 标识
	 * @return 前端返回格式
	 */
    Result findById(Long userId, Long id);
    /**
	 * 条件分页查询
	 *
	 * @param userId userId
	 * @param page 当前页
	 * @param size 显示多少条
	 * @param sort 排序字段
	 * @param regionCities 查询条件
	 * @return 前端返回格式
	 */
    Result pageQuery(Long userId, Integer page, Integer size, String sort, RegionCities regionCities);
}
