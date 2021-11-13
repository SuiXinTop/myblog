package com.spring.myblog.service.impl;

import com.spring.myblog.entity.SysLog;
import com.spring.myblog.dao.SysLogDao;
import com.spring.myblog.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SysLog)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 12:54:13
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
    @Resource
    private SysLogDao sysLogDao;

}