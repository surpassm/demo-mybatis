package com.liaoin.demo.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import javax.validation.constraints.Min;
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
@NameStyle(Style.camelhump)
@Table(name = "t_power")
@org.hibernate.annotations.Table(appliesTo = "t_power", comment = "权限表")
public class Power {

    @Id
    @Min(0)
    @KeySql(useGeneratedKeys = true)
    @Column(columnDefinition = "bigint COMMENT '系统标识'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) COMMENT '名称'")
    private String name;

    @Column(columnDefinition = "datetime COMMENT '创建时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @Column(columnDefinition = "datetime COMMENT '修改时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ManyToOne(targetEntity = UserInfo.class)
    @JoinColumn(name = "create_user_id", referencedColumnName = "id")
    private Long createUserId;

    @ManyToOne(targetEntity = UserInfo.class)
    @JoinColumn(name = "update_user_id", referencedColumnName = "id")
    private Long updateUserId;
}
