package com.kaxin.qkcustomermanage.exception;

import com.kaxin.qkcustomermanage.entity.ResultCode;

import java.io.Serializable;

/**
 * 默认的异常处理
 *
 * @author Administrator
 */
public class KaXinException extends RuntimeException implements ResultCode, Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 错误码
     */
    protected Integer code;
    /**
     * 错误信息
     */
    protected String msg;

    public KaXinException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public KaXinException(String msg) {
        super(msg);
        this.code = 500;
        this.msg = msg;
    }

    public KaXinException(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMsg());
    }

    public KaXinException(ResultCode resultCode, Object... args) {
        this(resultCode.getCode(), String.format(resultCode.getMsg(), args));
    }

    public KaXinException(ResultCode resultCode, String str, Boolean flag) {
        this(resultCode.getCode(), resultCode.getMsg() + ":" + str);
    }

    //非法请求
    public static KaXinException badRequest() {
        return new KaXinException(5001, "非法请求");
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
