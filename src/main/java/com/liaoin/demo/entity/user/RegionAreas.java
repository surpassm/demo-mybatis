package com.liaoin.demo.entity.user;

import com.github.surpassm.common.service.InsertView;
import com.github.surpassm.common.service.UpdateView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@ApiModel(description = "区县信息表")
@NameStyle(Style.camelhump)
@Table(name = "t_region_areas")
@org.hibernate.annotations.Table(appliesTo = "t_region_areas", comment = "区县信息表")
public class RegionAreas {
	@Id
	@Min(0)
	@KeySql(useGeneratedKeys = true)
	@ApiModelProperty(value = "系统标识")
	@Column(columnDefinition="bigint COMMENT '系统标识'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(groups = UpdateView.class,message = "参数不能为空")
	private Long id;

	@ApiModelProperty("区县编码")
	@Column(columnDefinition="varchar(255) COMMENT '区县编码'")
	@NotBlank(groups = {InsertView.class,UpdateView.class},message = "参数不能为为空或空串")
	private String code ;

	@ApiModelProperty("名称")
	@Column(columnDefinition="varchar(255) COMMENT '名称'")
	@NotBlank(groups = {InsertView.class,UpdateView.class},message = "参数不能为为空或空串")
	private String name ;

	@ApiModelProperty("所属城市编码")
	@Column(columnDefinition="varchar(255) COMMENT '所属城市编码'")
	@NotBlank(groups = {InsertView.class,UpdateView.class},message = "参数不能为为空或空串")
	private String citiesCode ;
}
