package com.spring.blog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.DesensitizedUtil;
import com.spring.blog.dao.UserDao;
import com.spring.blog.service.UserService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.constant.RedisConstant;
import com.spring.common.enmu.Status;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.po.User;
import com.spring.common.exception.ServiceException;
import com.spring.common.exception.user.EmailCodeNotExitException;
import com.spring.common.exception.user.EmailCodeNotMatchException;
import com.spring.common.util.SecurityUtil;
import com.spring.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * (MyUser)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:54:50
 */
@Service("myUserService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RedisService redisService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg register(User user, String code) {
        String email = user.getUserEmail();

        if (!redisService.hasKey(RedisConstant.EMAIL_PREFIX + email)) {
            throw new EmailCodeNotExitException();
        }
        if (!code.equals(redisService.get(RedisConstant.EMAIL_PREFIX + email))) {
            throw new EmailCodeNotMatchException();
        }
        user.setRegisterTime(new DateTime());
        user.setUserPassword(SecurityUtil.getMd5Hex(user.getUserPassword()));
        if (userDao.insert(user) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.REGISTER_FAULT);
        }
        return RestMsg.success(MsgConstant.REGISTER_SUCCESS, null);
    }

    @Override
    public RestMsg update(User user) {
        if (userDao.updateById(user) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, null);
    }

    @Override
    public RestMsg updateSecurity(User myUser, String code) {
        String email = myUser.getUserEmail();
        if (!redisService.hasKey(RedisConstant.EMAIL_PREFIX + email)) {
            throw new EmailCodeNotExitException();
        }
        if (code.equals(redisService.get(RedisConstant.EMAIL_PREFIX + email))) {
            throw new EmailCodeNotMatchException();
        }

        User user = User.builder().userId(myUser.getUserId()).userEmail(email).build();
        if (myUser.getUserPassword() != null) {
            user.setUserPassword(SecurityUtil.getMd5Hex(myUser.getUserPassword()));
            user.setUserEmail(null);
        }
        if (userDao.updateById(user) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, null);
    }

    @Override
    public RestMsg select(Integer userId) {
        User user = User.builder().userId(userId).build();
        User result = userDao.selectAllByUserId(user);
        if (result == null) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }

        result.setUserPassword(null);
        result.setUserEmail(DesensitizedUtil.email(result.getUserEmail()));

        return RestMsg.success(MsgConstant.SELECT_SUCCESS, result);
    }


    @Override
    public RestMsg delete(Integer userId) {
        if (userDao.deleteById(userId) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.DELETE_USER_SUCCESS);
        }
        return RestMsg.success(MsgConstant.DELETE_USER_FAULT, null);
    }
}