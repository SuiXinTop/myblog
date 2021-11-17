package com.spring.blog.service.impl;

import com.spring.blog.dao.MyAttendDao;
import com.spring.blog.service.MyAttendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (MyAttend)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:53:05
 */
@Service("myAttendService")
public class MyAttendServiceImpl implements MyAttendService {
    @Resource
    private MyAttendDao myAttendDao;

}