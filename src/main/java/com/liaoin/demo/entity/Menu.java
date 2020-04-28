package com.liaoin.demo.entity;

import lombok.*;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author mc
 * @version 1.0
 * @date 2018/9/25 9:24
 * @description
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.camelhump)
@Table
@org.hibernate.annotations.Table(appliesTo = "menu", comment = "菜单")
public class Menu implements Serializable {

	@Id
	@Min(0)
	@KeySql(useGeneratedKeys = true)
	@Column(columnDefinition="bigint COMMENT '系统标识'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@OneToOne(targetEntity = Menu.class)
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Long parentId;

	@Column(columnDefinition="int(11) COMMENT '菜单排序'")
    private Integer menuIndex;

	@Column(columnDefinition="int(11) COMMENT '权限分类（0 菜单；1 功能）'")
    private Integer type;


	@Column(columnDefinition="varchar(255) COMMENT '名称'")
    private String name;

	@Column(columnDefinition="varchar(255) COMMENT '描述'")
    private String describes;

	@Column(columnDefinition="varchar(255) COMMENT '路由路径 前端使用'")
    private String path;

	@Column(columnDefinition="varchar(255) COMMENT '菜单图标名称'")
    private String menuIcon;

	@Column(columnDefinition="varchar(255) COMMENT '菜单url后台权限控制'")
    private String menuUrl;

	@Min(0)
	@Max(1)
	private Integer isDelete;



}
