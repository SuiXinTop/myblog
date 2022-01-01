package com.spring.blog.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.UserDao;
import com.spring.blog.service.UserService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.constant.RedisConstant;
import com.spring.common.enmu.Status;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.dto.UserRegister;
import com.spring.common.entity.dto.UserSecurity;
import com.spring.common.entity.po.User;
import com.spring.common.entity.vo.UserVo;
import com.spring.common.exception.ServiceException;
import com.spring.common.exception.user.EmailCodeNotExitException;
import com.spring.common.exception.user.EmailCodeNotMatchException;
import com.spring.common.util.SecurityUtil;
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
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RedisService redisService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg register(UserRegister user) {

        if (!redisService.hasKey(RedisConstant.EMAIL_PREFIX + user.getUserEmail())) {
            throw new EmailCodeNotExitException();
        }
        if (!user.getCode().equals(redisService.get(RedisConstant.EMAIL_PREFIX + user.getUserEmail()))) {
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
        redisService.del(RedisConstant.USER_INFO + user.getUserId());

        if (user.getUserPassword() != null) {
            user.setUserPassword(SecurityUtil.getMd5Hex(user.getUserPassword()));
        }

        if (userDao.updateById(user) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }

        redisService.del(RedisConstant.USER_INFO + user.getUserId());
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg recoverState(List<Integer> userIdList) {
        if (userIdList.isEmpty()) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        userIdList.forEach(userId -> {
            if (userDao.updateUserState(userId) == Status.Exception.ordinal()) {
                throw new ServiceException(MsgConstant.UPDATE_FAULT);
            }
        });
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, null);
    }

    @Override
    public RestMsg updateRole(Integer userId, Integer roleId) {
        User update = User.builder().userId(userId).roleId(roleId).build();
        if (userDao.updateById(update) == Status.Exception.ordinal()) {
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

        User update = UserUtil.createSecurityEmail(user);

        if (userDao.updateById(update) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }

        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, null);
    }

    @Override
    public RestMsg selectByUserId(Integer userId) {
        UserVo user;
        if (redisService.hasKey(RedisConstant.USER_INFO + userId)) {
            user = (UserVo) redisService.get(RedisConstant.USER_INFO + userId);
            return RestMsg.success(user);
        }

        user = userDao.selectAllByUserId(userId);
        if (user == null) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        user.setUserEmail(DesensitizedUtil.email(user.getUserEmail()));

        redisService.setExpire(RedisConstant.USER_INFO + userId, user, RedisConstant.USER_INFO_EXPIRE_TIME);

        return RestMsg.success(MsgConstant.SELECT_SUCCESS, user);
    }

    @Override
    public RestMsg selectNormal(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserVo> userVoList = userDao.selectNormal();
        if (userVoList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }

        userVoList.stream().map(User::getUserEmail)
                .forEach(DesensitizedUtil::email);

        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(userVoList));
    }

    @Override
    public RestMsg selectException(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserVo> userVoList = userDao.selectException();
        if (userVoList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }

        userVoList.stream().map(User::getUserEmail)
                .forEach(DesensitizedUtil::email);

        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(userVoList));
    }

    @Override
    public RestMsg selectByUserName(String userName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserVo> result = userDao.selectAllByUserName(userName);
        if (result.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(result));
    }


    @Override
    public RestMsg delete(Integer userId) {
        if (userDao.deleteById(userId) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.DELETE_USER_FAULT);
        }
        return RestMsg.success(MsgConstant.DELETE_USER_SUCCESS, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg deleteList(List<Integer> userIdList) {
        if (userIdList.isEmpty()) {
            throw new ServiceException(MsgConstant.DELETE_USER_FAULT);
        }
        if (userDao.deleteBatchIds(userIdList) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.DELETE_USER_FAULT);
        }
        return RestMsg.success(MsgConstant.DELETE_USER_SUCCESS, null);
    }

}