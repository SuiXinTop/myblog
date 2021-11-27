package com.spring.auth.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import com.spring.auth.dao.UserDao;
import com.spring.auth.service.AuthService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.constant.RedisConstant;
import com.spring.common.constant.RoleConstant;
import com.spring.common.constant.UserConstant;
import com.spring.common.entity.bo.UserMap;
import com.spring.common.entity.dto.EmailCode;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.dto.UserLogin;
import com.spring.common.entity.po.User;
import com.spring.common.exception.user.*;
import com.spring.common.util.RequestUtil;
import com.spring.common.util.SecurityUtil;
import com.spring.common.util.TokenUtil;
import com.spring.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    private final UserDao userDao;
    private final RedisService redisService;
    private final HttpServletRequest request;

    @Override
    public RestMsg login(UserLogin user) {
        if (!redisService.hasKey(RedisConstant.LOGIN_TIMES_PREFIX + user.getUserEmail())) {
            redisService.setExpire(RedisConstant.LOGIN_TIMES_PREFIX + user.getUserEmail(),
                    1, RedisConstant.LOGIN_EXPIRE_TIME);
        } else {
            int times = (int) redisService.get(RedisConstant.LOGIN_TIMES_PREFIX + user.getUserEmail());
            if (times > RedisConstant.LOGIN_LIMIT) {
                throw new UserPasswordRetryLimitCountException(times);
            }
            redisService.increment(RedisConstant.LOGIN_TIMES_PREFIX + user.getUserEmail(), 1);
        }

        UserMap result = userDao.selectAllByEmail(user.getUserEmail());
        if (result == null) {
            throw new UserNotExistsException();
        }
        if (result.getUserState() == UserConstant.EXCEPTION) {
            throw new UserDeleteException();
        }
        if (!SecurityUtil.verifyPassword(user.getUserPassword(), result.getUserPassword())) {
            throw new UserPasswordNotMatchException();
        }

        String loginIp = RequestUtil.getIpAddress(request);
        Date loginTime = new DateTime();

        //修改登陆时间和ip
        User update = User.builder().userId(result.getUserId())
                .loginIp(loginIp)
                .loginTime(loginTime).build();
        userDao.updateById(update);

        result.setLoginIp(loginIp);
        result.setLoginTime(loginTime);
        result.setUserPassword(null);
        result.setUserEmail(DesensitizedUtil.email(result.getUserEmail()));

        String token = TokenUtil.createToken(result);
        String key = RedisConstant.TOKEN_PREFIX + SecurityUtil.getMd5Key(token, loginIp);
        redisService.setExpire(key, result, RedisConstant.TOKEN_EXPIRE_TIME);
        return RestMsg.success(MsgConstant.LOGIN_SUCCESS, token);
    }

    @Override
    public RestMsg admin(UserLogin user) {
        if (!redisService.hasKey(RedisConstant.LOGIN_TIMES_PREFIX + user.getUserEmail())) {
            redisService.setExpire(RedisConstant.LOGIN_TIMES_PREFIX + user.getUserEmail(),
                    1, RedisConstant.LOGIN_EXPIRE_TIME);
        } else {
            int times = (int) redisService.get(RedisConstant.LOGIN_TIMES_PREFIX + user.getUserEmail());
            if (times > RedisConstant.LOGIN_LIMIT) {
                throw new UserPasswordRetryLimitCountException(times);
            }
            redisService.increment(RedisConstant.LOGIN_TIMES_PREFIX + user.getUserEmail(), 1);
        }

        UserMap result = userDao.selectAllByEmail(user.getUserEmail());
        if (result == null) {
            throw new UserNotExistsException();
        }

        String roleKey = result.getRole().getRoleKey();
        if (RoleConstant.ADMIN_LIST.stream().noneMatch(s -> s.equals(roleKey))) {
            throw new UserNotExistsException();
        }

        if (result.getUserState() == UserConstant.EXCEPTION) {
            throw new UserDeleteException();
        }
        if (!SecurityUtil.verifyPassword(user.getUserPassword(), result.getUserPassword())) {
            throw new UserPasswordNotMatchException();
        }

        String loginIp = RequestUtil.getIpAddress(request);
        Date loginTime = new DateTime();

        //修改登陆时间和ip
        User update = User.builder()
                .userId(result.getUserId())
                .loginIp(RequestUtil.getIpAddress(request))
                .loginTime(new DateTime()).build();
        userDao.updateById(update);

        result.setLoginIp(loginIp);
        result.setLoginTime(loginTime);
        result.setUserPassword(null);
        result.setUserEmail(DesensitizedUtil.email(result.getUserEmail()));

        String token = TokenUtil.createToken(result);
        String key = RedisConstant.TOKEN_PREFIX + SecurityUtil.getMd5Key(token, loginIp);
        redisService.setExpire(key, result, RedisConstant.TOKEN_EXPIRE_TIME);
        return RestMsg.success(MsgConstant.LOGIN_SUCCESS, token);
    }


    @Override
    public RestMsg login(EmailCode emailCode) {

        String valid = (String) redisService.get(RedisConstant.EMAIL_PREFIX + emailCode.getEmail());

        if (StrUtil.isEmpty(valid)) {
            throw new EmailCodeNotExitException();
        }

        if (!emailCode.getCode().equals(valid)) {
            throw new EmailCodeNotMatchException();
        }

        UserMap result = userDao.selectAllByEmail(emailCode.getEmail());

        if (result == null) {
            throw new UserNotExistsException();
        }

        String loginIp = RequestUtil.getIpAddress(request);
        Date loginTime = new DateTime();

        //修改登陆时间和ip
        User update = User.builder()
                .userId(result.getUserId())
                .loginIp(RequestUtil.getIpAddress(request))
                .loginTime(new DateTime()).build();
        userDao.updateById(update);

        result.setLoginIp(loginIp);
        result.setLoginTime(loginTime);
        result.setUserPassword(null);
        result.setUserEmail(DesensitizedUtil.email(result.getUserEmail()));

        String token = TokenUtil.createToken(result);
        String key = RedisConstant.TOKEN_PREFIX + SecurityUtil.getMd5Key(token, loginIp);
        redisService.setExpire(key, result, RedisConstant.TOKEN_EXPIRE_TIME);
        return RestMsg.success(MsgConstant.LOGIN_SUCCESS, token);
    }

    @Override
    public RestMsg logout(String token) {
        String ip = RequestUtil.getIpAddress(request);
        String key = RedisConstant.TOKEN_PREFIX + SecurityUtil.getMd5Key(token, ip);
        if (!redisService.del(key)) {
            return RestMsg.fail(MsgConstant.LOGOUT_FAULT);
        }
        return RestMsg.success(MsgConstant.LOGOUT_SUCCESS);
    }
}
