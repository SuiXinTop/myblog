package com.spring.blog.service.impl;

import cn.hutool.core.util.StrUtil;
import com.spring.blog.dao.MyUserDao;
import com.spring.blog.redis.RedisService;
import com.spring.blog.service.AuthService;
import com.spring.common.constant.MapKeyConstant;
import com.spring.common.constant.MsgConstant;
import com.spring.common.constant.RedisConstant;
import com.spring.common.constant.UserConstant;
import com.spring.common.entity.EmailCode;
import com.spring.common.entity.MyUser;
import com.spring.common.exception.user.*;
import com.spring.common.model.RestMsg;
import com.spring.common.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-13
 * @描述
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MyUserDao myUserDao;
    private final RedisService redisService;

    @Override
    public RestMsg login(MyUser myUser) {
        MyUser test = myUserDao.selectUserRole(myUser);
        if (test == null) {
            throw new UserNotExistsException();
        }
        if (test.getUserState().equals(UserConstant.EXCEPTION)) {
            throw new UserDeleteException();
        }
        if (!test.getUserPassword().equals(myUser.getUserPassword())) {
            throw new UserPasswordNotMatchException();
        }
        log.info(test.getMyRole().getRoleName());
        Map<String, Object> map = TokenUtil.returnToken(test);
        redisService.setExpire(RedisConstant.TOKEN_PREFIX + map.get(MapKeyConstant.TOKEN),
                test, RedisConstant.TOKEN_EXPIRE_TIME);
        return RestMsg.success(MsgConstant.LOGIN_SUCCESS, map);
    }

    @Override
    public RestMsg emailLogin(EmailCode emailCode) {
        String valid = (String) redisService.get(RedisConstant.EMAIL_PREFIX + emailCode.getEmail());
        if (StrUtil.isEmpty(valid)) {
            throw new EmailCodeNotExitException();
        }
        if (!emailCode.getCode().equals(valid)) {
            throw new EmailCodeNotMatchException();
        }
        MyUser myUser = MyUser.builder().userEmail(emailCode.getEmail()).build();
        MyUser test = myUserDao.selectUserRole(myUser);
        if (test == null) {
            throw new UserNotExistsException();
        }
        Map<String, Object> map = TokenUtil.returnToken(test);
        redisService.setExpire(RedisConstant.TOKEN_PREFIX + map.get(MapKeyConstant.TOKEN),
                test, RedisConstant.TOKEN_EXPIRE_TIME);
        redisService.del(RedisConstant.EMAIL_PREFIX+emailCode.getEmail());
        return RestMsg.success(MsgConstant.LOGIN_SUCCESS, map);
    }

    @Override
    public void refresh(String token) {
        redisService.expire(RedisConstant.TOKEN_PREFIX + token, RedisConstant.TOKEN_EXPIRE_TIME);
    }

    @Override
    public RestMsg logout(String token) {
        redisService.del(RedisConstant.TOKEN_PREFIX + token);
        return RestMsg.success(MsgConstant.LOGOUT_SUCCESS);
    }
}
