package com.spring.auth.service.impl;

import com.spring.auth.dao.UserDao;
import com.spring.auth.service.EmailService;
import com.spring.auth.util.EmailUtil;
import com.spring.common.entity.dto.EmailCode;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.exception.user.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author xxx
 * @create 2021-10-10
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final UserDao userDao;
    private final EmailUtil emailUtil;

    @Override
    public RestMsg sendVerifyEmail(EmailCode emailCode) {
        if (userDao.selectExitByEmail(emailCode.getEmail()) == 0) {
            throw new UserException("该邮箱尚未注册");
        }
        emailUtil.sendHtmlEmail(emailCode);
        return RestMsg.success("发送成功", null);
    }

    @Override
    public RestMsg sendRegisterMail(EmailCode emailCode) {
        if (userDao.selectExitByEmail(emailCode.getEmail()) != 0) {
            throw new UserException("该邮箱已被使用");
        }
        emailUtil.sendHtmlEmail(emailCode);
        return RestMsg.success("发送成功", null);
    }
}
