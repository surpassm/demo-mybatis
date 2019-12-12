package com.liaoin.demo.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * @author mc
 * Create date 2019/8/24 13:50
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
@Table(name = "t_region_areas")
@org.hibernate.annotations.Table(appliesTo = "t_region_areas", comment = "区县信息表")
public class RegionAreas {
	@Id
	@Min(0)
	@KeySql(useGeneratedKeys = true)
	@Column(columnDefinition="bigint COMMENT '系统标识'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition="varchar(255) COMMENT '区县编码'")
	private String code ;

	@Column(columnDefinition="varchar(255) COMMENT '名称'")
	private String name ;

	@Column(columnDefinition="varchar(255) COMMENT '所属城市编码'")
	private String citiesCode ;
}
