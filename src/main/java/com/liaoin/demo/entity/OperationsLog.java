package com.liaoin.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Log
 *
 * @author zhangquanli
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "日志管理")
@NameStyle(Style.camelhump)
@Table
@org.hibernate.annotations.Table(appliesTo = "operations_log", comment = "日志管理")
public class OperationsLog implements Serializable {
	@Id
	@KeySql(useGeneratedKeys = true)
	@Column(columnDefinition="bigint COMMENT '系统标识'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 模块
     */
    private String module;
    /**
     * 功能
     */
    private String functions;
    /**
     * 接口
     */
    private String uri;
    /**
     * 数据
     */
    @Lob
    private String data;
    /**
     * 客户端IP
     */
    private String clientIp;
    /**
     * 操作开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operateStartTime;
    /**
     * 操作结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operateEndTime;

    /**
     * 用户主键
     */
    @ManyToOne(targetEntity = UserInfo.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Long userId;
}
