package com.spring.blog.service.impl;

import com.spring.blog.dao.MyUserDao;
import com.spring.blog.service.MyUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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