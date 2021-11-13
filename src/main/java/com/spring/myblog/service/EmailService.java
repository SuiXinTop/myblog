package com.spring.myblog.service;

/**
 * @author STARS
 */
public interface EmailService {

    /**
     * Send html mail.
     *
     * @param path the path
     * @param code the code
     */
    void sendHtmlMail(String path, String code);
}
