package com.spring.blog.service;

import com.spring.common.entity.dto.EmailCode;
import com.spring.common.entity.dto.RestMsg;

/**
 * @author STARS
 */
public interface EmailService {

    /**
     * Send html mail.
     *
     * @param emailCode the email code
     * @return the rest msg
     */
    RestMsg verifyMail(EmailCode emailCode);

    /**
     * Send register mail rest msg.
     *
     * @param emailCode the email code
     * @return the rest msg
     */
    RestMsg registerMail(EmailCode emailCode);

}
