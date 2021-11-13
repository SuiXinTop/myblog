package com.spring.myblog.service.impl;

import cn.hutool.core.util.StrUtil;
import com.spring.myblog.common.constant.MapKeyConstant;
import com.spring.myblog.common.constant.MsgConstant;
import com.spring.myblog.common.constant.RedisConstant;
import com.spring.myblog.common.constant.UserConstant;
import com.spring.myblog.common.exception.user.*;
import com.spring.myblog.common.model.RestMsg;
import com.spring.myblog.common.util.RedisService;
import com.spring.myblog.common.util.TokenUtil;
import com.spring.myblog.dao.MyUserDao;
import com.spring.myblog.entity.MyUser;
import com.spring.myblog.service.AuthService;
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
    public RestMsg emailLogin(String email, String code) {
        String valid = (String) redisService.get(RedisConstant.EMAIL_PREFIX + email);
        if (StrUtil.isEmpty(valid)) {
            throw new EmailCodeNotExitException();
        }
        if (!code.equals(valid)) {
            throw new EmailCodeNotMatchException();
        }
        MyUser myUser = MyUser.builder().userEmail(email).build();
        MyUser test = myUserDao.selectUserRole(myUser);
        if (test == null) {
            throw new UserNotExistsException();
        }
        Map<String, Object> map = TokenUtil.returnToken(test);
        redisService.setExpire(RedisConstant.TOKEN_PREFIX + map.get(MapKeyConstant.TOKEN),
                test, RedisConstant.TOKEN_EXPIRE_TIME);
        redisService.del(RedisConstant.EMAIL_PREFIX+email);
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
