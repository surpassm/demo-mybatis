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
@Table(name = "t_user_info", uniqueConstraints = {@UniqueConstraint(columnNames="username")})
@org.hibernate.annotations.Table(appliesTo = "t_user_info", comment = "用户")
public class UserInfo implements Serializable {


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

    @Column(columnDefinition = "bigint COMMENT '排序字段'")
    private Long userInfoIndex;

    @Column(columnDefinition = "datetime COMMENT '最后登陆时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime landingTime;


	@Min(0)
	@Max(1)
	@Column(columnDefinition = "int(1) COMMENT '是否启用0=否,1=是'")
	private Integer isEnable;

	@Min(0)
	@Max(1)
	@Column(columnDefinition = "int(1) COMMENT '是否删除0=否,1=是'")
	private Integer isDelete;

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

}
