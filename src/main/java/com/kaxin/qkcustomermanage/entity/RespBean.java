package com.kaxin.qkcustomermanage.entity;

import com.kaxin.qkcustomermanage.exception.KaXinException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tangdj
 */
@Data
@NoArgsConstructor
public class RespBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int SUCCESS_CODE = 200;

    private static final int ERROR_CODE = 500;

    @ApiModelProperty(value = "状态码", required = true, example = "200")
    private int code;

    @ApiModelProperty(value = "消息", required = true, example = "操作成功")
    private String msg;

    private T data;

    private RespBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private RespBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> RespBean<T> ok() {
        return new RespBean<>(SUCCESS_CODE, "操作成功");
    }

    public static <T> RespBean<T> ok(T data) {
        return new RespBean<>(SUCCESS_CODE, "操作成功", data);
    }

    public static <T> RespBean<T> ok(String msg) {
        return new RespBean<>(SUCCESS_CODE, msg);
    }

    public static <T> RespBean<T> ok(String msg, T data) {
        return new RespBean<>(SUCCESS_CODE, msg, data);
    }

    public static <T> RespBean<T> error() {
        return new RespBean<>(ERROR_CODE, "操作失败", null);
    }

    public static <T> RespBean<T> error(String msg) {
        return new RespBean<>(ERROR_CODE, msg, null);
    }

    public static <T> RespBean<T> error(int code, String msg) {
        return new RespBean<>(code, msg, null);
    }

    public static <T> RespBean<T> error(String msg, T data) {
        return new RespBean<>(ERROR_CODE, msg, data);
    }

    public static <T> RespBean<T> error(KaXinException e) {
        return new RespBean<>(e.getCode(), e.getMessage(), null);
    }

    public static <T> RespBean<T> error(ResultCode resultCode) {
        return new RespBean<>(resultCode.getCode(), resultCode.getMsg(), null);
    }

    public static <T> RespBean<T> error(int code, String msg, T data) {
        return new RespBean<>(code, msg, data);
    }
}
