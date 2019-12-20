package com.liaoin.demo.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
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
import java.util.*;

/**
 * @author mc
 * Create date 2019/1/21 11:05
 * Version 1.0
 * Description
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NameStyle(Style.camelhump)
@org.hibernate.annotations.Table(appliesTo = "t_user_info", comment = "用户")
@Table(name = "t_user_info", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "union_id", "open_id"})})
public class UserInfo {


    @Id
    @Min(0)
    @KeySql(useGeneratedKeys = true)
    @Column(columnDefinition = "bigint COMMENT '系统标识'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", columnDefinition = "varchar(255) COMMENT '账号'")
    private String username;
    @Column(columnDefinition = "varchar(255) COMMENT '密码'")
    private String password;

    @Column(columnDefinition = "varchar(255) COMMENT '头像URL'")
    private String headUrl;

    @Column(columnDefinition = "varchar(255) COMMENT 'sessionKey'")
    private String sessionKey;

    @Column(name = "open_id", columnDefinition = "varchar(255) COMMENT 'openId'")
    private String openId;

    @Column(name = "union_id", columnDefinition = "varchar(255) COMMENT 'unionId'")
    private String unionId;

    @Column(columnDefinition = "varchar(255) COMMENT '昵称'")
    private String nickName;

    @Column(columnDefinition = "bigint COMMENT '排序字段'")
    private Long userInfoIndex;

    @Column(columnDefinition = "datetime COMMENT '最后登陆时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime landingTime;
    @Min(0)
    @Max(1)
    private Integer isDelete;
	private String gender;
	private String language;
	private String city;
	private String province;
	private String country;
	private String avatarUrl;


    @Column(columnDefinition = "varchar(255) COMMENT '公司名称'")
    private String companyName;
    @Column(columnDefinition = "varchar(255) COMMENT '职位'")
    private String position;
    @Column(columnDefinition = "varchar(255) COMMENT '姓名'")
    private String name;
    @Column(columnDefinition = "varchar(255) COMMENT '电话'")
    private String mobile;
    @Column(columnDefinition = "varchar(255) COMMENT 'email'")
    private String email;


}
