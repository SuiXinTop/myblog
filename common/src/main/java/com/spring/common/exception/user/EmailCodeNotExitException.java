package com.spring.common.exception.user;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-13
 * @描述
 */
public class EmailCodeNotExitException extends UserException{
    private static final long serialVersionUID = 1L;

    public EmailCodeNotExitException() {
        super("未发送验证邮件");
    }
}
