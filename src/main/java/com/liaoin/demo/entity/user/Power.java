package com.liaoin.demo.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.surpassm.common.service.InsertView;
import com.github.surpassm.common.service.UpdateView;
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
import java.time.LocalDateTime;

/**
 * @author mc
 * Create date 2019/11/17 19:43
 * Version 1.0
 * Description 权限表
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "权限表")
@NameStyle(Style.camelhump)
@Table(name = "t_power")
@org.hibernate.annotations.Table(appliesTo = "t_power", comment = "权限表")
public class Power {

    @Id
    @Min(0)
    @KeySql(useGeneratedKeys = true)
    @ApiModelProperty(value = "系统标识")
    @Column(columnDefinition = "bigint COMMENT '系统标识'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = UpdateView.class, message = "参数不能为空")
    private Long id;
    @ApiModelProperty(value = "名称")
    @Column(columnDefinition = "varchar(255) COMMENT '名称'")
    @NotBlank(groups = {InsertView.class, UpdateView.class}, message = "参数不能为为空或空串")
    private String name;

    @Min(0)
    @Max(1)
    @ApiModelProperty(value = "是否删除", hidden = true)
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @Column(columnDefinition = "datetime COMMENT '创建时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "用户系统标识")
    @ManyToOne(targetEntity = UserInfo.class)
    @JoinColumn(name = "create_user_id", referencedColumnName = "id")
    @NotNull(groups = {InsertView.class, UpdateView.class}, message = "参数不能为为空")
    private Long createUserId;
}
