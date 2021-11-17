package com.spring.blog.service;

import com.spring.common.entity.EmailCode;

/**
 * @author STARS
 */
public interface EmailService {

    /**
     * Send html mail.
     *
     * @param emailCode the email code
     */
    void sendHtmlMail(EmailCode emailCode);
}
