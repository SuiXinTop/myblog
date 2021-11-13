package com.spring.myblog.service.impl;

import com.spring.myblog.entity.MyCollect;
import com.spring.myblog.dao.MyCollectDao;
import com.spring.myblog.service.MyCollectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MyCollect)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:53:42
 */
@Service("myCollectService")
public class MyCollectServiceImpl implements MyCollectService {
    @Resource
    private MyCollectDao myCollectDao;

}