package com.spring.common.handler;

import com.spring.common.entity.dto.RestMsg;
import com.spring.common.exception.ServiceException;
import com.spring.common.exception.UnauthorizedException;
import com.spring.common.exception.user.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestMsg handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数异常", e);
        //打印校验住的所有的错误信息
        StringBuilder msg = new StringBuilder("参数错误：[");
        List<ObjectError> list = e.getAllErrors();
        for (ObjectError item : list) {
            msg.append(item.getDefaultMessage()).append(',');
        }
        msg.deleteCharAt(msg.length() - 1);
        msg.append(']');
        return RestMsg.fail(msg.toString());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public RestMsg handleUnauthorizedException(UnauthorizedException e) {
        return RestMsg.fail(401,e.getMessage(),null);
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
     * 业务
     */
    @ExceptionHandler(ServiceException.class)
    public RestMsg handleServiceException(ServiceException e) {
        log.error("发生业务异常.", e);
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
