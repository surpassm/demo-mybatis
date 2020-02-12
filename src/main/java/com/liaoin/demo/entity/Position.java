package com.liaoin.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
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
 * Create date 2020/2/1 13:24
 * Version 1.0
 * Description
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "职位")
@NameStyle(Style.camelhump)
@Table(name = "t_position")
@org.hibernate.annotations.Table(appliesTo = "t_position", comment = "职位")
public class Position implements Serializable {

	@Id
	@Min(0)
	@KeySql(useGeneratedKeys = true)
	@Column(columnDefinition="bigint COMMENT '系统标识'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition="varchar(255) COMMENT '名称'")
	private String name ;

	@OneToOne(targetEntity = Department.class)
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	private Long parentId ;

	@Column(columnDefinition="datetime COMMENT '创建时间'")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime createTime;

	@ManyToOne(targetEntity = Department.class)
	@JoinColumn(name = "department_id", referencedColumnName = "id")
	private Long departmentId;

	@Min(0)
	@Max(1)
	private Integer isDelete;

	@Column(columnDefinition="datetime COMMENT '修改时间'")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime updateTime;

	@Column(columnDefinition="datetime COMMENT '修改时间'")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime deleteTime;
}
