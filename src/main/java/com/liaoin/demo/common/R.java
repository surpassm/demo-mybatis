package com.liaoin.demo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 前端JSON返回格式,自定义响应格式
 *
 * @author mc
 * version 1.0
 * date 2018/10/30 12:52
 * description
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> {
    /**
     * 响应业务状态
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应中的数据
     */
    private T data;


    public R(T data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMsg();
        this.data = data;
    }

    public R(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public R(Long total, List<T> rows) {
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMsg();
        this.data = (T) new PageData(total,rows);

    }

    public R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> R<T> ok(T data) {
        return new R<>(data);
    }

    public static <T> R<T> ok(Long total, List<T> rows) {
        return new R<>(total, rows);
    }

    public static R ok() {
        return new R<>("");
    }

    public static R fail() {
        return new R(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
    }

    public static R fail(String msg) {
        return new R(ResultCode.ERROR.getCode(), msg);
    }

    public static R fail(Integer code, String msg) {
        return new R(code, msg);
    }


    public static R fail(Object data) {
        return new R<>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg(), data);
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Getter
    @Setter
    public class PageData<T>{
        /**
         * 分页数据
         */
        private Long total;
        /**
         * 分页返回
         */
        private List<T> rows;

        PageData(Long total, List<T> rows){
            this.total = total;
            this.rows = rows;
        }
    }

}
