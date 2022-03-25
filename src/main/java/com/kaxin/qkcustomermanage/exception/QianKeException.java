package com.kaxin.qkcustomermanage.exception;

import com.kaxin.qkcustomermanage.entity.ResultCode;

import java.io.Serializable;

/**
 * 功能描述
 *
 * @author tangdj
 * @date 2022/3/25
 */
public class QianKeException extends RuntimeException implements ResultCode, Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 错误码
     */
    protected Integer code;
    /**
     * 错误信息
     */
    protected String msg;

    public QianKeException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public QianKeException(String msg) {
        super(msg);
        this.code = 500;
        this.msg = msg;
    }

    public QianKeException(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMsg());
    }

    public QianKeException(ResultCode resultCode, Object... args) {
        this(resultCode.getCode(), String.format(resultCode.getMsg(), args));
    }

    public QianKeException(ResultCode resultCode, String str, Boolean flag) {
        this(resultCode.getCode(), resultCode.getMsg() + ":" + str);
    }

    //非法请求
    public static QianKeException badRequest() {
        return new QianKeException(5001, "非法请求");
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
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
}
