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
 * Create date 2019/11/28 10:13
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
@Table(name = "m_department_user_info")
@org.hibernate.annotations.Table(appliesTo = "m_department_user_info", comment = "部门用户关系表")
public class DepartmentUserInfo implements Serializable {

    @Id
    @Min(0)
    @KeySql(useGeneratedKeys = true)
    @Column(columnDefinition="bigint COMMENT '系统标识'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Long departmentId;

    @ManyToOne(targetEntity = UserInfo.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Long userId;
}
