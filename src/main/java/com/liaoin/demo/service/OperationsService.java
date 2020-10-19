package com.liaoin.demo.service;

import com.liaoin.demo.common.R;
import com.liaoin.demo.domain.OperationsDTO;
import com.liaoin.demo.domain.OperationsVO;
import com.liaoin.demo.entity.Operations;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 后台功能接口接口
  */
public interface OperationsService {
    /**
	 * 新增
	 *
	 * @param operations 对象
	 * @return 前端返回格式
	 */
	Operations insert(Operations operations);

    /**
	 * 修改
	 *
	 * @param operations 对象
	 */
    void update(Operations operations);

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
	Optional<Operations> findById(@NotNull @Min(1) Long id);

    /**
	 * 条件分页查询
	 *
	 * @param page 当前页
	 * @param size 显示多少条
	 * @param sort 排序字段
	 * @param vo 查询条件
	 * @return 前端返回格式
	 */
    R pageQuery(Integer page, Integer size, String sort, OperationsVO vo);


	/**
	* 新增
	*
	* @param vo vo
	* @return Banner
	*/
	Operations insertOrUpdate(OperationsVO vo);

	/**
	 * 查询所有父级
	 * @return OperationsDTO
	 */
	List<OperationsDTO> findAllParent();

	/**
	 * 根据父级ID查询所有子级
	 * @param parentId parentId
	 * @return OperationsDTO
	 */
	List<OperationsDTO> findAllChild(Long parentId);

	/**
	 * 根据用户id获取接口列表
	 * @param userId userId
	 * @return Operations
	 */
	List<Operations> findByUserId(Long userId);
}
