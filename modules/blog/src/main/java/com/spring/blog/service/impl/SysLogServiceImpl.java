package com.spring.blog.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.SysLogDao;
import com.spring.blog.service.SysLogService;
import com.spring.common.constant.Dictionary;
import com.spring.common.constant.MsgConstant;
import com.spring.common.enmu.Status;
import com.spring.common.entity.po.SysLog;
import com.spring.common.exception.ServiceException;
import com.spring.common.entity.dto.RestMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (SysLog)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 12:54:13
 */
@Slf4j
@Service("sysLogService")
@RequiredArgsConstructor
public class SysLogServiceImpl implements SysLogService {
    private final SysLogDao sysLogDao;

    @Override
    public RestMsg select(Integer status, String startTime, String endTime, int pageNum, int pageSize) {

        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        if (status == Status.Normal.ordinal() || status == Status.Exception.ordinal()) {
            queryWrapper.eq(Dictionary.OPER_STATUS, status);
        }
        queryWrapper.between(StrUtil.isNotEmpty(startTime) && StrUtil.isNotEmpty(endTime),
                        Dictionary.OPER_TIME, DateUtil.parse(startTime), DateUtil.parse(endTime));

        queryWrapper.orderByDesc(Dictionary.LOG_ID);

        PageHelper.startPage(pageNum, pageSize);
        List<SysLog> list = sysLogDao.selectList(queryWrapper);
        if (list.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(list));
    }
}