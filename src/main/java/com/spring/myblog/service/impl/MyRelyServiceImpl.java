package com.spring.myblog.service.impl;

import com.spring.myblog.entity.MyRely;
import com.spring.myblog.dao.MyRelyDao;
import com.spring.myblog.service.MyRelyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MyRely)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:54:04
 */
@Service("myRelyService")
public class MyRelyServiceImpl implements MyRelyService {
    @Resource
    private MyRelyDao myRelyDao;

}