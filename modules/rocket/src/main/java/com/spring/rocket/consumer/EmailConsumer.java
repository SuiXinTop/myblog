package com.spring.rocket.consumer;

import com.spring.common.constant.EmailConstant;
import com.spring.common.constant.RedisConstant;
import com.spring.common.constant.Topic;
import com.spring.common.entity.dto.EmailCode;
import com.spring.common.exception.ServiceException;
import com.spring.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-13
 * @描述
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = Topic.EMAIL_CODE, consumerGroup = "consumer-email")
public class EmailConsumer implements RocketMQListener<EmailCode> {
    private final RedisService redisService;
    private final JavaMailSenderImpl mailSender;

    @Override
    public void onMessage(EmailCode emailCode) {
        log.info("收到发送邮件消息");
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mineHelper = new MimeMessageHelper(message, true);
            //谁发
            mineHelper.setFrom(EmailConstant.FROM);
            //谁要接收
            mineHelper.setTo(emailCode.getEmail());
            //邮件主题
            mineHelper.setSubject(EmailConstant.SUBJECT);
            //邮件内容   true 表示带有附件或html
            String content = EmailConstant.CONTENT_PREFIX + emailCode.getCode() + EmailConstant.CONTENT_SUFFIX;
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
