package com.liaoin.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author mc
 * Create date 2019/3/14 18:06
 * Version 1.0
 * Description
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "组权限")
@NameStyle(Style.camelhump)
@Table
@org.hibernate.annotations.Table(appliesTo = "groups_menu", comment = "组权限")
public class GroupsMenu implements Serializable {



	@Id
	@Min(0)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@KeySql(useGeneratedKeys = true)
	@ApiModelProperty(value="系统标识")
	@Column(columnDefinition="bigint COMMENT '系统标识'")
	public Long id;

	@ApiModelProperty(value="组系统标识",example = "1")
	@ManyToOne(targetEntity = Groups.class)
	@JoinColumn(name = "group_id", referencedColumnName = "id")
	private Long groupId;

	@ApiModelProperty(value="权限系统标识",example = "1")
	@ManyToOne(targetEntity = Menu.class)
	@JoinColumn(name = "menu_id", referencedColumnName = "id")
	private Long menuId;

	@ApiModelProperty(value="权限类型0=可访问、1=可授权",example = "1",allowableValues = "0,1")
	@Column(columnDefinition="int(1) COMMENT '权限类型0=可访问、1=可授权'",nullable = false)
	private Integer menuType;
}
