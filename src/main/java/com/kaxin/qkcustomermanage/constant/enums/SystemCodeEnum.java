package com.kaxin.qkcustomermanage.constant.enums;


import com.kaxin.qkcustomermanage.entity.ResultCode;

/**
 * @author tangdj
 * 系统响应错误代码枚举类
 */

public enum SystemCodeEnum implements ResultCode {
    //系统响应成功
    SYSTEM_OK(0, "success"),
    //未捕获的错误
    SYSTEM_ERROR(500, "网络异常，请稍后重试"),
    //未登录
    SYSTEM_NOT_LOGIN(302, "请先登录"),
    //拒绝访问
    SYSTEM_BAD_REQUEST(403, "请求频率过快,请稍后再试"),
    //无权访问
    SYSTEM_NO_AUTH(401, "无权操作"),
    //资源未找到
    SYSTEM_NO_FOUND(404, "资源未找到"),
    //资源未找到
    SYSTEM_NO_VALID(400, "参数验证错误"),
    //请求方式错误
    SYSTEM_METHOD_ERROR(405, "请求方式错误"),
    //请求超时
    SYSTEM_REQUEST_TIMEOUT(408, "请求超时"),
    //服务调用异常
    SYSTEM_SERVER_ERROR(1001, "服务调用异常"),
    //参数不存在
    SYSTEM_NO_SUCH_PARAM_ERROR(1003, "参数不存在"),
    //上传文件失败
    SYSTEM_UPLOAD_FILE_ERROR(1004, "文件上传失败"),
    //下载文件失败
    SYSTEM_DOWNLOAD_FILE_ERROR(1005, "文件下载失败"),
    //API签名错误
    SYSTEM_API_AUTH_SIGN_ERROR(1006, "签名验证失败"),
    //API签名过期
    SYSTEM_API_AUTH_SIGN_EXPIRE(1007, "签名超时"),
    ;

    private int code;
    private String msg;

    SystemCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static SystemCodeEnum parse(Integer code) {
        for (SystemCodeEnum value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return SystemCodeEnum.SYSTEM_ERROR;
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
