package com.spring.blog.service.impl;

import com.spring.blog.dao.MyRoleDao;
import com.spring.blog.service.MyRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (MyRole)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:54:13
 */
@Service("myRoleService")
public class MyRoleServiceImpl implements MyRoleService {
    @Resource
    private MyRoleDao myRoleDao;

}