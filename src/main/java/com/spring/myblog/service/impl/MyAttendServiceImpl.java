package com.spring.myblog.service.impl;

import com.spring.myblog.entity.MyAttend;
import com.spring.myblog.dao.MyAttendDao;
import com.spring.myblog.service.MyAttendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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