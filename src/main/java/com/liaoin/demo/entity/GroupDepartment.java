package com.liaoin.demo.entity;

import lombok.*;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author mc
 * Create date 2020/2/1 13:18
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
@Table(name = "m_group_department")
@org.hibernate.annotations.Table(appliesTo = "m_group_department", comment = "组部门")
public class GroupDepartment implements Serializable {
	@Id
	@Min(0)
	@KeySql(useGeneratedKeys = true)
	@Column(columnDefinition="bigint COMMENT '系统标识'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(targetEntity = Group.class)
	@JoinColumn(name = "group_id", referencedColumnName = "id")
	private Long groupId;

	@ManyToOne(targetEntity = Department.class)
	@JoinColumn(name = "department_id", referencedColumnName = "id")
	private Long departmentId;

}
