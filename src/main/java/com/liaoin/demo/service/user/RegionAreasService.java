package com.liaoin.demo.service.user;

import com.github.surpassm.common.jackson.Result;
import com.liaoin.demo.entity.user.RegionAreas;

/**
  * @author mc
  * Create date 2019-11-28 10:47:51
  * Version 1.0
  * Description 区县信息表接口
  */
public interface RegionAreasService {
    /**
	 * 新增
	 *
	 * @param token token
	 * @param regionAreas 对象
	 * @return 前端返回格式
	 */
    Result insert(String token, RegionAreas regionAreas);
    /**
	 * 修改
	 *
	 * @param token token
	 * @param regionAreas 对象
	 * @return 前端返回格式
	 */
    Result update(String token, RegionAreas regionAreas);
    /**
	 * 根据主键删除
	 *
	 * @param token token
	 * @param id 标识
	 * @return 前端返回格式
	 */
    Result deleteGetById(String token, Long id);
    /**
	 * 根据主键查询
	 *
	 * @param token token
	 * @param id 标识
	 * @return 前端返回格式
	 */
    Result findById(String token, Long id);
    /**
	 * 条件分页查询
	 *
	 * @param token token
	 * @param page 当前页
	 * @param size 显示多少条
	 * @param sort 排序字段
	 * @param regionAreas 查询条件
	 * @return 前端返回格式
	 */
    Result pageQuery(String token, Integer page, Integer size, String sort, RegionAreas regionAreas);
}