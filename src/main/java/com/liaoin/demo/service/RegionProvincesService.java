package com.liaoin.demo.service;

import com.liaoin.demo.common.Result;
import com.liaoin.demo.domain.RegionProvincesVO;
import com.liaoin.demo.entity.RegionProvinces;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 省接口
  */
public interface RegionProvincesService {
    /**
	 * 新增
	 *
	 * @param regionProvinces 对象
	 * @return 前端返回格式
	 */
	RegionProvinces insert(RegionProvinces regionProvinces);

    /**
	 * 修改
	 *
	 * @param regionProvinces 对象
	 * @return 前端返回格式
	 */
    void update(RegionProvinces regionProvinces);

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
	Optional<RegionProvinces> findById(@NotNull @Min(1) Long id);

    /**
	 * 条件分页查询
	 *
	 * @param page 当前页
	 * @param size 显示多少条
	 * @param sort 排序字段
	 * @param vo 查询条件
	 * @return 前端返回格式
	 */
    Result pageQuery(Integer page, Integer size, String sort, RegionProvincesVO vo);


	/**
	* 新增
	*
	* @param vo vo
	* @return Banner
	*/
	RegionProvinces insertVO(RegionProvincesVO vo);

	/**
	* 修改
	*
	* @param vo vo
	* @return Banner
	*/
	RegionProvinces updateVO(RegionProvincesVO vo);
}
