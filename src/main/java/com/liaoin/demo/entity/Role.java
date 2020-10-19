package com.liaoin.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table
@org.hibernate.annotations.Table(appliesTo = "role", comment = "角色")
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

	@Min(0)
	@Max(1)
	@Column(columnDefinition="int(1) COMMENT '是否启用0=否、1=是'")
	private Integer isEnable;

	@Column(columnDefinition="datetime COMMENT '创建时间'")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime createTime;
	@Column(columnDefinition="datetime COMMENT '修改时间'")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime updateTime;
	@Column(columnDefinition="datetime COMMENT '修改时间'")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime deleteTime;


	@Transient
	private Boolean isBind;

}
