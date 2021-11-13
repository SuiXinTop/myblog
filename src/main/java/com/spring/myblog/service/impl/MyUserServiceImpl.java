package com.spring.myblog.service.impl;

import com.spring.myblog.entity.MyUser;
import com.spring.myblog.dao.MyUserDao;
import com.spring.myblog.service.MyUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MyUser)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:54:50
 */
@Service("myUserService")
public class MyUserServiceImpl implements MyUserService {
    @Resource
    private MyUserDao myUserDao;

}