package com.liaoin.demo.mapper;

import com.liaoin.demo.domain.MenuDTO;
import com.liaoin.demo.entity.Menu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author mc
 * Create date 2020-02-10 10:15:20
 * Version 1.0
 * Description 权限持久层
 */
public interface MenuMapper extends tk.mybatis.mapper.common.Mapper<Menu>, MySqlMapper<Menu> {

	/**
	 * 查询所有父级
	 *
	 * @return
	 */
	List<MenuDTO> findAllParent();

	/**
	 * 根据父级ID查询所有子级
	 *
	 * @param parentId
	 * @return
	 */
	List<MenuDTO> findAllChild(@Param("parentId") Long parentId);

	/**
	 * 根据用户ID查询菜单
	 *
	 * @param userId 用户ID
	 * @return 集合
	 */
	List<MenuDTO> findByUserId(Long userId);
}
