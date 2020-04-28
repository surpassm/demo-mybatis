package com.liaoin.demo.service;

import com.liaoin.demo.common.Result;
import com.liaoin.demo.domain.MenuDTO;
import com.liaoin.demo.domain.MenuVO;
import com.liaoin.demo.entity.Menu;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 权限接口
  */
public interface MenuService {
    /**
	 * 新增
	 *
	 * @param menu 对象
	 * @return 前端返回格式
	 */
	Menu insert(Menu menu);

    /**
	 * 修改
	 *
	 * @param menu 对象
	 */
    void update(Menu menu);

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
	Optional<Menu> findById(@NotNull @Min(1) Long id);

    /**
	 * 条件分页查询
	 *
	 * @param page 当前页
	 * @param size 显示多少条
	 * @param sort 排序字段
	 * @param vo 查询条件
	 * @return 前端返回格式
	 */
    Result pageQuery(Integer page, Integer size, String sort, MenuVO vo);


	/**
	* 新增
	*
	* @param vo vo
	* @return Banner
	*/
	Menu insertVO(MenuVO vo);

	/**
	* 修改
	*
	* @param vo vo
	* @return Banner
	*/
	Menu updateVO(MenuVO vo);

	/**
	 * 查询所有父级
	 * @return
	 */
	List<MenuDTO> findAllParent();

	/**
	 * 根据父级ID查询所有子级
	 * @param parentId
	 * @return
	 */
	List<MenuDTO> findAllChild(Long parentId);

	/**
	 * 根据父级Id查询所有子级
	 * @param menuId
	 * @return
	 */
	List<Menu> findByParentId(Long menuId);

	/**
	 * 根据用户ID查询菜单
	 * @param userId
	 * @return
	 */
	List<MenuDTO> findByUserId(Long userId);
}
