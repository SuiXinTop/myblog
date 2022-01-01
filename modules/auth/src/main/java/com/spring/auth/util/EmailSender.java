package com.spring.auth.util;

import com.spring.common.constant.EmailConstant;
import com.spring.common.constant.RedisConstant;
import com.spring.common.entity.dto.EmailCode;
import com.spring.common.exception.ServiceException;
import com.spring.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-12-06
 * @描述
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSender {
    private final RedisService redisService;
    private final JavaMailSenderImpl mailSender;

    @Async
    public void sendHtmlEmail(EmailCode emailCode,String subject,String contentPrefix,String contentSuffix){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mineHelper = new MimeMessageHelper(message, true);
            mineHelper.setFrom(EmailConstant.FROM);
            mineHelper.setTo(emailCode.getEmail());
            mineHelper.setSubject(subject);

            String content = contentPrefix + emailCode.getCode() + contentSuffix;
            mineHelper.setText(content, true);

            mailSender.send(message);
            redisService.setExpire(RedisConstant.EMAIL_PREFIX + emailCode.getEmail(),
                    emailCode.getCode(), RedisConstant.EMAIL_EXPIRE_TIME);

            log.info("已向{}发送邮件", emailCode.getEmail());
        } catch (Exception e) {
            throw new ServiceException("邮件发送失败");
        }
    }
}
