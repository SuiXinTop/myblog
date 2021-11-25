package com.spring.blog.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.UserDao;
import com.spring.blog.service.UserService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.constant.RedisConstant;
import com.spring.common.enmu.Status;
import com.spring.common.entity.bo.UserMap;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.dto.UserRegister;
import com.spring.common.entity.dto.UserSecurity;
import com.spring.common.entity.po.User;
import com.spring.common.exception.ServiceException;
import com.spring.common.exception.user.EmailCodeNotExitException;
import com.spring.common.exception.user.EmailCodeNotMatchException;
import com.spring.common.util.UserUtil;
import com.spring.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public RestMsg register(UserRegister user) {

        String email = user.getUserEmail();

        if (!redisService.hasKey(RedisConstant.EMAIL_PREFIX + email)) {
            throw new EmailCodeNotExitException();
        }
        if (!user.getCode().equals(redisService.get(RedisConstant.EMAIL_PREFIX + email))) {
            throw new EmailCodeNotMatchException();
        }

        User register = UserUtil.createRegisterUser(user);

        if (userDao.insert(register) == Status.Exception.ordinal()) {
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
    public RestMsg updateSecurity(UserSecurity user) {

        String email = user.getUserEmail();
        if (!redisService.hasKey(RedisConstant.EMAIL_PREFIX + email)) {
            throw new EmailCodeNotExitException();
        }
        if (user.getCode().equals(redisService.get(RedisConstant.EMAIL_PREFIX + email))) {
            throw new EmailCodeNotMatchException();
        }

        User update;
        if (user.getUserPassword() != null) {
            update = UserUtil.createSecurityPassword(user);
        } else {
            update = UserUtil.createSecurityEmail(user);
        }

        if (userDao.updateById(update) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }

        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, null);
    }

    @Override
    public RestMsg selectByUserId(Integer userId) {

        UserMap result = userDao.selectAllByUserId(userId);
        if (result == null) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }

        result.setUserPassword(null);
        result.setUserEmail(DesensitizedUtil.email(result.getUserEmail()));

        return RestMsg.success(MsgConstant.SELECT_SUCCESS, result);
    }

    @Override
    public RestMsg selectByUserName(String userName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserMap> result = userDao.selectAllByUserName(userName);
        if (result.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(result));
    }


    @Override
    public RestMsg delete(Integer userId) {
        if (userDao.deleteById(userId) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.DELETE_USER_SUCCESS);
        }
        return RestMsg.success(MsgConstant.DELETE_USER_FAULT, null);
    }
}