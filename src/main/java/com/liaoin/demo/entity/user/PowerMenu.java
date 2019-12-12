package com.liaoin.demo.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author mc
 * Create date 2019/11/17 19:44
 * Version 1.0
 * Description 权限与菜单关联表
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.camelhump)
@Table(name = "m_power_menu")
@org.hibernate.annotations.Table(appliesTo = "m_power_menu", comment = "权限与菜单关联表")
public class PowerMenu {
	@Id
	@Min(0)
	@KeySql(useGeneratedKeys = true)
	@Column(columnDefinition="bigint COMMENT '系统标识'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(targetEntity = Power.class)
	@JoinColumn(name = "power_id", referencedColumnName = "id")
	private Long powerId;
	@ManyToOne(targetEntity = Menu.class)
	@JoinColumn(name = "menu_id", referencedColumnName = "id")
	private Long menuId;
	@Min(0)
	@Max(1)
	@Column(columnDefinition="int(1) COMMENT '权限类型'",nullable = false)
	private Integer menuType;
}
