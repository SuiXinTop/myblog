package com.spring.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.AttendDao;
import com.spring.blog.service.AttendService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.enmu.Status;
import com.spring.common.entity.bo.AttendMap;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.po.Attend;
import com.spring.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (MyAttend)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:53:05
 */
@Service("myAttendService")
@RequiredArgsConstructor
public class AttendServiceImpl implements AttendService {
    private final AttendDao attendDao;

    @Override
    public RestMsg insert(Attend attend) {
        attend.setAttendTime(new DateTime());
        if (attendDao.insert(attend) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }
        return RestMsg.success(MsgConstant.INSERT_SUCCESS, null);
    }

    @Override
    public RestMsg selectAttend(Integer fansUserId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AttendMap> attendList = attendDao.selectAttend(fansUserId);
        if (attendList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(attendList));
    }

    @Override
    public RestMsg selectFans(Integer attendUserId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AttendMap> myFansList = attendDao.selectFans(attendUserId);
        if (myFansList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(myFansList));
    }

    @Override
    public RestMsg delete(List<Integer> attendIds) {
        if (attendDao.deleteBatchIds(attendIds) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }
        return RestMsg.success(MsgConstant.DELETE_SUCCESS, null);
    }

}