package com.spring.blog.service.impl;

import com.spring.blog.dao.UserDao;
import com.spring.blog.service.EmailService;
import com.spring.common.constant.Topic;
import com.spring.common.entity.po.User;
import com.spring.common.exception.user.UserException;
import com.spring.common.entity.dto.EmailCode;
import com.spring.common.entity.dto.RestMsg;
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
    private final UserDao userDao;
    private final RocketMQTemplate rocketMQTemplate;

    @Override
    public RestMsg verifyMail(EmailCode emailCode) {

        rocketMQTemplate.sendOneWay(Topic.EMAIL_CODE, emailCode);
        return RestMsg.success("发送成功", null);
    }

    @Override
    public RestMsg registerMail(EmailCode emailCode) {
        User user = User.builder().userEmail(emailCode.getEmail()).build();
        if (userDao.selectAllByEmail(user) != null) {
            throw new UserException("该邮箱已被使用");
        }
        return verifyMail(emailCode);
    }
}