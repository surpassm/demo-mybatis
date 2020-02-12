package com.liaoin.demo.domain;


import com.liaoin.demo.annotation.Convert;
import com.liaoin.demo.entity.Menu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


/**
* @author mc
* Create date 2020-02-10 10:15:20
* Version 1.0
* Description 权限VO数据流
*/

@Data
@ApiModel(value = "权限VO")
public class MenuVO implements Serializable {
	@ApiModelProperty(value = "系统标识",position = 0)
	private Long id;
	@ApiModelProperty(value = "父级Id",position = 1)
	private Long parentId;
	@ApiModelProperty(value = "菜单排序",position = 2)
	private Integer menuIndex;
	@ApiModelProperty(value = "权限分类（0 菜单；1 功能）",position = 3)
	private Integer type;

	@ApiModelProperty(value = "名称",position = 4)
	private String name;
	@ApiModelProperty(value = "描述",position = 5)
	private String describes;
	@ApiModelProperty(value = "路由路径 前端使用",position = 6)
	private String path;
	@ApiModelProperty(value = "菜单图标名称",position = 7)
	private String menuIcon;
	@ApiModelProperty(value = "菜单url后台权限控制",position = 8)
	private String menuUrl;



    public Menu convertTo(){
        ConvertImpl impl = new ConvertImpl();
        return impl.doForward(this);
    }

    public MenuVO convertFor(Menu menu){
        ConvertImpl impl = new ConvertImpl();
        return impl.doBackward(menu,this);
    }

    private static class ConvertImpl implements Convert<MenuVO, Menu> {
        @Override
        public Menu doForward(MenuVO vo) {
            Menu menu = new Menu();
            BeanUtils.copyProperties(vo,menu);
            return menu;
        }
        @Override
        public MenuVO doBackward(Menu menu) {
                MenuVO vo = new MenuVO();
                BeanUtils.copyProperties(menu, vo);
                return vo;
        }
        public MenuVO doBackward(Menu menu, MenuVO vo) {
                BeanUtils.copyProperties(menu, vo);
                return vo;
        }
    }




}
