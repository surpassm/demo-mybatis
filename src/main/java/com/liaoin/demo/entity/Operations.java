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
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author mc
 * Create date 2019/11/17 19:54
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
@Table(name = "t_operations")
@org.hibernate.annotations.Table(appliesTo = "t_operations", comment = "后台功能接口")
public class Operations implements Serializable {
	@Id
	@Min(0)
	@KeySql(useGeneratedKeys = true)
	@Column(columnDefinition="bigint COMMENT '系统标识'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(targetEntity = Operations.class)
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	private Long parentId;

	@Column(columnDefinition="int(11) COMMENT '排序'")
	private Integer menuIndex;

	@Column(columnDefinition="int(11) COMMENT '权限分类（0 菜单；1 功能）'")
	private Integer type;

	@Column(columnDefinition="varchar(255) COMMENT '名称'")
	private String name;

	@NotBlank(message = "参数不能为为空或空串")
	@Column(columnDefinition="varchar(255) COMMENT '描述'")
	private String describes;

	@Column(columnDefinition="varchar(255) COMMENT '菜单url后台权限控制'")
	private String apiUrl;

	@Column(columnDefinition = "datetime COMMENT '创建时间'")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createTime;

	@Min(0)
	@Max(1)
	private Integer isDelete;
}
