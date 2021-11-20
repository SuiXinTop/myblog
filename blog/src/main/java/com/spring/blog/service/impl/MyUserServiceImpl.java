package com.spring.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.spring.blog.dao.MyUserDao;
import com.spring.blog.redis.RedisService;
import com.spring.blog.service.MyUserService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.constant.RedisConstant;
import com.spring.common.entity.MyUser;
import com.spring.common.exception.ServiceException;
import com.spring.common.exception.user.EmailCodeNotExitException;
import com.spring.common.exception.user.EmailCodeNotMatchException;
import com.spring.common.model.RestMsg;
import com.spring.common.util.SecurityUtil;
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
public class MyUserServiceImpl implements MyUserService {
    private final MyUserDao myUserDao;
    private final RedisService redisService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg register(MyUser myUser, String code) {
        String email = myUser.getUserEmail();

        if (!redisService.hasKey(RedisConstant.EMAIL_PREFIX + email)) {
            throw new EmailCodeNotExitException();
        }
        if (code.equals(redisService.get(RedisConstant.EMAIL_PREFIX + email))) {
            throw new EmailCodeNotMatchException();
        }
        myUser.setRegisterTime(new DateTime());
        myUser.setUserPassword(SecurityUtil.getMd5Hex(myUser.getUserPassword()));
        if (myUserDao.insert(myUser) == 0) {
            throw new ServiceException("注册失败");
        }
        return RestMsg.success("注册成功", "");
    }

    @Override
    public RestMsg update(MyUser myUser) {
        if (myUserDao.updateById(myUser) == 0) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, "");
    }

    @Override
    public RestMsg updateSecurity(MyUser myUser, String code) {
        String email = myUser.getUserEmail();
        if (!redisService.hasKey(RedisConstant.EMAIL_PREFIX + email)) {
            throw new EmailCodeNotExitException();
        }
        if (code.equals(redisService.get(RedisConstant.EMAIL_PREFIX + email))) {
            throw new EmailCodeNotMatchException();
        }

        MyUser user = MyUser.builder().userId(myUser.getUserId()).userEmail(email).build();
        if (myUser.getUserPassword() != null) {
            user.setUserPassword(SecurityUtil.getMd5Hex(myUser.getUserPassword()));
            user.setUserEmail(null);
        }
        if (myUserDao.updateById(user) == 0) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, "");
    }

    @Override
    public RestMsg select(Integer userId) {
        MyUser myUser = MyUser.builder().userId(userId).build();
        MyUser result = myUserDao.selectUserRole(myUser);
        if (result == null) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, result);
    }


    @Override
    public RestMsg delete(Integer userId) {
        if (myUserDao.deleteById(userId) == 0) {
            throw new ServiceException("销户失败");
        }
        return RestMsg.success("销户成功", "");
    }
}