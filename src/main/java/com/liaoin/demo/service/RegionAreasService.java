package com.liaoin.demo.service;

import com.liaoin.demo.common.R;
import com.liaoin.demo.domain.RegionAreasVO;
import com.liaoin.demo.entity.RegionAreas;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 区县信息表接口
  */
public interface RegionAreasService {
    /**
	 * 新增
	 *
	 * @param regionAreas 对象
	 * @return 前端返回格式
	 */
	RegionAreas insert(RegionAreas regionAreas);

    /**
	 * 修改
	 *
	 * @param regionAreas 对象
	 */
    void update(RegionAreas regionAreas);

    /**
	 * 根据主键删除
	 *
	 * @param id 标识
	 */
	void deleteById(@NotNull @Min(1) Long id);

    /**
	 * 根据主键查询
	 *
	 * @param id 标识
	 * @return 前端返回格式
	 */
	Optional<RegionAreas> findById(@NotNull @Min(1) Long id);

    /**
	 * 条件分页查询
	 *
	 * @param page 当前页
	 * @param size 显示多少条
	 * @param sort 排序字段
	 * @param vo 查询条件
	 * @return 前端返回格式
	 */
    R pageQuery(Integer page, Integer size, String sort, RegionAreasVO vo);


	/**
	* 新增
	*
	* @param vo vo
	* @return Banner
	*/
	RegionAreas insertVO(RegionAreasVO vo);

	/**
	* 修改
	*
	* @param vo vo
	* @return Banner
	*/
	RegionAreas updateVO(RegionAreasVO vo);

	/**
	 * 根据城市代码查询区县
	 * @param cityCode
	 * @return
	 */
	R pageQueryByCityCode(String cityCode);
}
