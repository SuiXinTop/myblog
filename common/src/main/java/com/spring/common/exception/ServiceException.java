package com.spring.common.exception;

/**
 * 业务异常
 * 
 * @author ruoyi
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected final String message;

    public ServiceException(String message) {
        super(message);
        this.message = message;
    }

    public ServiceException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
