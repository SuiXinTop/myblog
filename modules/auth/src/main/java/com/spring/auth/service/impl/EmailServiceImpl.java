package com.spring.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.spring.auth.dao.UserDao;
import com.spring.auth.service.EmailService;
import com.spring.auth.util.EmailSender;
import com.spring.common.constant.EmailConstant;
import com.spring.common.constant.RedisConstant;
import com.spring.common.entity.dto.EmailCode;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.exception.user.EmailCodeNotExitException;
import com.spring.common.exception.user.EmailCodeNotMatchException;
import com.spring.common.exception.user.UserException;
import com.spring.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xxx
 * @create 2021-10-10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final UserDao userDao;
    private final RedisService redisService;
    private final EmailSender emailSender;

    @Override
    public RestMsg sendVerifyEmail(EmailCode emailCode) {
        if (userDao.selectExitByEmail(emailCode.getEmail()) == 0) {
            throw new UserException("该邮箱尚未注册");
        }
        emailSender.sendHtmlEmail(emailCode, EmailConstant.SUBJECT_VERIFY,
                EmailConstant.VERIFY_CONTENT_PREFIX,EmailConstant.VERIFY_CONTENT_SUFFIX);
        return RestMsg.success("发送成功", null);
    }

    @Override
    public RestMsg verifyEmailCode(EmailCode emailCode){
        String valid = (String) redisService.get(RedisConstant.EMAIL_PREFIX + emailCode.getEmail());
        log.info(emailCode.getEmail(),emailCode.getCode());
        if (StrUtil.isEmpty(valid)) {
            throw new EmailCodeNotExitException();
        }

        if (!emailCode.getCode().equals(valid)) {
            throw new EmailCodeNotMatchException();
        }

        return RestMsg.success(null);
    }

    @Override
    public RestMsg sendRegisterMail(EmailCode emailCode) {
        if (userDao.selectExitByEmail(emailCode.getEmail()) != 0) {
            throw new UserException("该邮箱已被使用");
        }
        emailSender.sendHtmlEmail(emailCode, EmailConstant.SUBJECT_REGISTER,
                EmailConstant.REGISTER_CONTENT_PREFIX,EmailConstant.REGISTER_CONTENT_SUFFIX);
        return RestMsg.success("发送成功", null);
    }
}
