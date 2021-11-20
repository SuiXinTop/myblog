package com.spring.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.MyRelyDao;
import com.spring.blog.service.MyRelyService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.MyRely;
import com.spring.common.exception.ServiceException;
import com.spring.common.model.RestMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (MyRely)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:54:04
 */
@Service("myRelyService")
@RequiredArgsConstructor
public class MyRelyServiceImpl implements MyRelyService {
    private final MyRelyDao myRelyDao;

    @Override
    public RestMsg insert(MyRely myRely) {
        myRely.setReplyTime(new DateTime());
        if (myRelyDao.insert(myRely) == 0) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }
        return RestMsg.success(MsgConstant.INSERT_SUCCESS, "");
    }

    @Override
    public RestMsg update(MyRely myRely) {
        myRely.setReplyTime(new DateTime());
        if (myRelyDao.updateById(myRely) == 0) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, "");
    }

    @Override
    public RestMsg delete(List<Integer> replyIds) {
        if (replyIds.isEmpty()) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }
        if (myRelyDao.deleteBatchIds(replyIds) == 0) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }
        return RestMsg.success(MsgConstant.DELETE_SUCCESS, "");
    }

    @Override
    public RestMsg select(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyRely> myRelies = myRelyDao.selectAllByOwner(userId);
        if (myRelies.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(myRelies));
    }

}