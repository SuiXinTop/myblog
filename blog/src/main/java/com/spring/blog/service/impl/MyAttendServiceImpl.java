package com.spring.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.MyAttendDao;
import com.spring.blog.service.MyAttendService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.MyAttend;
import com.spring.common.exception.ServiceException;
import com.spring.common.model.RestMsg;
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
public class MyAttendServiceImpl implements MyAttendService {
    private final MyAttendDao myAttendDao;

    @Override
    public RestMsg insert(MyAttend myAttend) {
        myAttend.setAttendTime(new DateTime());
        if (myAttendDao.insert(myAttend) == 0) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }
        return RestMsg.success(MsgConstant.INSERT_SUCCESS, "");
    }

    @Override
    public RestMsg selectAttend(Integer fansUserId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyAttend> myAttendList = myAttendDao.selectAttend(fansUserId);
        if (myAttendList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(myAttendList));
    }

    @Override
    public RestMsg selectFans(Integer attendUserId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyAttend> myFansList = myAttendDao.selectFans(attendUserId);
        if (myFansList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(myFansList));
    }

    @Override
    public RestMsg delete(List<Integer> attendIds) {
        if (myAttendDao.deleteBatchIds(attendIds) == 0) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }
        return RestMsg.success(MsgConstant.DELETE_SUCCESS, "");
    }

}