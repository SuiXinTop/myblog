package com.spring.blog.service.impl;

import com.spring.common.entity.EmailCode;
import com.spring.blog.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xxx
 * @create 2021-10-10
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final RocketMQTemplate rocketMqTemplate;

    @Override
    public void sendHtmlMail(EmailCode emailCode) {
        rocketMqTemplate.sendOneWay("EmailCode", emailCode);
    }
}
