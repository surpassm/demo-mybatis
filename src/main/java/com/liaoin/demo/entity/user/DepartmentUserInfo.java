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
import javax.validation.constraints.NotNull;
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
@ApiModel(value = "部门用户关系表")
@NameStyle(Style.camelhump)
@Table(name = "m_department_user_info")
@org.hibernate.annotations.Table(appliesTo = "m_department_user_info", comment = "部门用户关系表")
public class DepartmentUserInfo implements Serializable {

    @Id
    @Min(0)
    @KeySql(useGeneratedKeys = true)
    @ApiModelProperty(value = "系统标识")
    @Column(columnDefinition="bigint COMMENT '系统标识'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = UpdateView.class,message = "参数不能为空")
    private Long id;

    @ApiModelProperty(value="组系统标识")
    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @NotNull(groups = {InsertView.class,UpdateView.class},message = "参数不能为为空")
    private Long departmentId;

    @ApiModelProperty(value="权限系统标识")
    @ManyToOne(targetEntity = UserInfo.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull(groups = {InsertView.class,UpdateView.class},message = "参数不能为为空")
    private Long userId;
}
