package com.liaoin.demo.service.user;

import com.liaoin.demo.common.Result;
import com.liaoin.demo.entity.user.PageElement;

/**
  * @author mc
  * Create date 2019-11-28 10:47:51
  * Version 1.0
  * Description 页面元素接口
  */
public interface PageElementService {
    /**
	 * 新增
	 *
	 * @param token token
	 * @param pageElement 对象
	 * @return 前端返回格式
	 */
    Result insert(Long userId, PageElement pageElement);
    /**
	 * 修改
	 *
	 * @param pageElement 对象
	 * @return 前端返回格式
	 */
    Result update(Long userId, PageElement pageElement);
    /**
	 * 根据主键删除
	 *
	 * @param id 标识
	 * @return 前端返回格式
	 */
    Result deleteGetById(Long userId, Long id);
    /**
	 * 根据主键查询
	 *
	 * @param id 标识
	 * @return 前端返回格式
	 */
    Result findById(Long userId, Long id);
    /**
	 * 条件分页查询
	 *
	 * @param page 当前页
	 * @param size 显示多少条
	 * @param sort 排序字段
	 * @param pageElement 查询条件
	 * @return 前端返回格式
	 */
    Result pageQuery(Long userId, Integer page, Integer size, String sort, PageElement pageElement);
}
