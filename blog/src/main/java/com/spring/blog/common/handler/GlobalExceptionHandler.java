package com.spring.blog.common.handler;

import com.spring.common.exception.user.UserException;
import com.spring.common.model.RestMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-07
 * @描述
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RestMsg handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.error("不支持请求,{}", e.getMethod());
        return RestMsg.fail("请求方式不支持");
    }

    /**
     * 用户异常
     */
    @ExceptionHandler(UserException.class)
    public RestMsg handleUserException(UserException e) {
        log.error("发生用户异常.", e);
        return RestMsg.fail(e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public RestMsg handleRuntimeException(RuntimeException e) {
        log.error("发生未知异常.", e);
        return RestMsg.fail(e.getMessage());
    }


    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public RestMsg handleException(Exception e) {
        log.error("发生系统异常.", e);
        return RestMsg.fail(e.getMessage());
    }
}
