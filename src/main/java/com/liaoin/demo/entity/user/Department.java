package com.liaoin.demo.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mc
 * Create date 2019/1/21 13:39
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
@Table(name = "t_department")
@org.hibernate.annotations.Table(appliesTo = "t_department", comment = "部门")
public class Department implements Serializable {

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

	@Column(columnDefinition="int(11) COMMENT '排序字段'")
	private Integer departmentIndex ;

	@Column(columnDefinition="datetime COMMENT '最后登陆时间'")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime createTime;

	@Min(0)
	@Max(1)
	private Integer isDelete;






}
