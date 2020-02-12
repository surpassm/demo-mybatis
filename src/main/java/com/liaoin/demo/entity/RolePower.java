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
 * Create date 2019/11/17 19:52
 * Version 1.0
 * Description
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.camelhump)
@Table(name = "m_role_power")
@org.hibernate.annotations.Table(appliesTo = "m_role_power", comment = "权限与角色关联表")
public class RolePower implements Serializable {
	@Id
	@Min(0)
	@KeySql(useGeneratedKeys = true)
	@Column(columnDefinition="bigint COMMENT '系统标识'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(targetEntity = Power.class)
	@JoinColumn(name = "power_id", referencedColumnName = "id")
	private Long powerId;
	@ManyToOne(targetEntity = Role.class)
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Long roleId;

	@Min(0)
	@Max(1)
	@Column(columnDefinition="int(1) COMMENT '权限类型0=可访问、1=可授权'",nullable = false)
	private Integer menuType;
}
