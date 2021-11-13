package com.spring.myblog.service.impl;

import com.spring.myblog.entity.MyRole;
import com.spring.myblog.dao.MyRoleDao;
import com.spring.myblog.service.MyRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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