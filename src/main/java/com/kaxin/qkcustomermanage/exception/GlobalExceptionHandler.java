package com.kaxin.qkcustomermanage.exception;


import com.kaxin.qkcustomermanage.constant.enums.SystemCodeEnum;
import com.kaxin.qkcustomermanage.entity.RespBean;
import com.kaxin.qkcustomermanage.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 全局异常处理类
 *
 * @author tangdj
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final int SYSTEM_EXCEPTION_CODE = 400;

    @ExceptionHandler(value = Exception.class)
    public RespBean defaultException(Exception ex) {
        if (ex instanceof ResultCode) {
            return RespBean.error(((ResultCode) ex).getCode(), ((ResultCode) ex).getMsg(), null);
        } else {
            //默认异常需要处理
            log.error("系统异常 Exception：{}", ex.getMessage());
        }
        return RespBean.error(SystemCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 处理 SpringMVC 请求参数缺失
     */
    @ExceptionHandler(value = SizeLimitExceededException.class)
    public RespBean missingServletRequestParameterExceptionHandler(SizeLimitExceededException ex) {
        return RespBean.error(SYSTEM_EXCEPTION_CODE, "上传文件总量过大 ！");
    }

    /**
     * 处理 SpringMVC 请求参数缺失
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public RespBean missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        return RespBean.error(SYSTEM_EXCEPTION_CODE, String.format("请求参数缺失: %s", ex.getParameterName()), null);
    }

    /**
     * 处理 SpringMVC 请求参数类型错误
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public RespBean methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        return RespBean.error(SYSTEM_EXCEPTION_CODE, String.format("请求参数类型错误: %s", ex.getMessage()), null);
    }

    /**
     * 处理 SpringMVC 请求参数类型错误
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RespBean httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        return RespBean.error(SYSTEM_EXCEPTION_CODE, "请求参数类型不匹配！", null);
    }

    /**
     * 处理 SpringMVC 参数校验不正确
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespBean methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        return RespBean.error(SYSTEM_EXCEPTION_CODE, String.format("请求参数不正确: %s", Objects.requireNonNull(fieldError).getDefaultMessage()), null);
    }

    /**
     * 处理 SpringMVC 参数绑定不正确，本质上也是通过 Validator 校验
     */
    @ExceptionHandler(BindException.class)
    public RespBean bindExceptionHandler(BindException ex) {
        FieldError fieldError = ex.getFieldError();
        return RespBean.error(SYSTEM_EXCEPTION_CODE, String.format("请求参数不正确: %s", Objects.requireNonNull(fieldError).getDefaultMessage()), null);
    }

    /**
     * 处理 SpringMVC 请求方式验证
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RespBean methodRequestExceptionHandler(HttpRequestMethodNotSupportedException ex, HttpServletResponse response) {
        return RespBean.error(SYSTEM_EXCEPTION_CODE, String.format("请求方式错误: %s", ex.getMethod()), null);
    }

    /**
     * 处理 客户端请求终端导致的BrokenPipe错误
     */
    @ExceptionHandler(value = ClientAbortException.class)
    public void clientAbortExceptionHandler(HttpServletRequest request) {
        log.warn("ClientAbortException请求中断, 请求URI: {}", request.getRequestURI());
    }

    /**
     * 处理 sql处理异常
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public RespBean dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex, HttpServletResponse response) {
        return RespBean.error(SYSTEM_EXCEPTION_CODE, String.format("sql执行错误: %s", ex.getMessage()), null);
    }

    @ExceptionHandler(value = ServletException.class)
    public RespBean servletException(ServletException e) {
        /*
          直接捕获NoHandlerFoundException异常gateway会报错，所以捕获ServletException
         */
        if (e instanceof NoHandlerFoundException) {
            return RespBean.error(SYSTEM_EXCEPTION_CODE, String.format("请求路径未找到: %s", ((NoHandlerFoundException) e).getRequestURL()), null);
        }
        return RespBean.error(SYSTEM_EXCEPTION_CODE, "请求失败", null);
    }
}
