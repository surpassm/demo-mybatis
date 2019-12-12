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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author mc
 * Create date 2019/1/21 13:24
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
@Table(name = "t_role")
@org.hibernate.annotations.Table(appliesTo = "t_role", comment = "角色")
public class Role implements Serializable {



	@Id
	@Min(0)
	@KeySql(useGeneratedKeys = true)
	@Column(columnDefinition="bigint COMMENT '系统标识'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(columnDefinition="varchar(255) COMMENT 'security标识'")
	private String securityRoles;

	@Column(columnDefinition="varchar(255) COMMENT '名称'")
	private String name;

	@Column(columnDefinition="varchar(255) COMMENT '描述'")
	private String describes;

	@Column(columnDefinition="int(11) COMMENT '排序字段'")
	private Integer roleIndex ;

	@Min(0)
	@Max(1)
	private Integer isDelete;


}
