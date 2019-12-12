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

/**
 * @author mc
 * Create date 2019/11/17 19:47
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
@Table(name = "m_power_page_element")
@org.hibernate.annotations.Table(appliesTo = "m_power_page_element", comment = "权限与页面关联表")
public class PowerPageElement {
	@Id
	@Min(0)
	@KeySql(useGeneratedKeys = true)
	@Column(columnDefinition="bigint COMMENT '系统标识'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(targetEntity = Power.class)
	@JoinColumn(name = "power_id", referencedColumnName = "id")
	private Long powerId;
	@ManyToOne(targetEntity = PageElement.class)
	@JoinColumn(name = "page_element_id", referencedColumnName = "id")
	private Long pageElementId;

	@Min(0)
	@Max(1)
	@Column(columnDefinition="int(1) COMMENT '权限类型0=可访问、1=可授权'",nullable = false)
	private Integer menuType;
}
