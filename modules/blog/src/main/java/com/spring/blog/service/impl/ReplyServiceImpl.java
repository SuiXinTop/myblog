package com.spring.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.ReplyDao;
import com.spring.blog.service.ReplyService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.enmu.Status;
import com.spring.common.entity.bo.ReplyMap;
import com.spring.common.entity.po.Reply;
import com.spring.common.exception.ServiceException;
import com.spring.common.entity.dto.RestMsg;
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
public class ReplyServiceImpl implements ReplyService {
    private final ReplyDao replyDao;

    @Override
    public RestMsg insert(Reply reply) {
        reply.setReplyTime(new DateTime());
        if (replyDao.insert(reply) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }
        return RestMsg.success(MsgConstant.INSERT_SUCCESS, null);
    }

    @Override
    public RestMsg update(Reply reply) {
        reply.setReplyTime(new DateTime());
        if (replyDao.updateById(reply) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, null);
    }

    @Override
    public RestMsg delete(List<Integer> replyIds) {
        if (replyIds.isEmpty()) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }
        if (replyDao.deleteBatchIds(replyIds) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }
        return RestMsg.success(MsgConstant.DELETE_SUCCESS, null);
    }

    @Override
    public RestMsg select(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ReplyMap> replyList = replyDao.selectAllByOwner(userId);
        if (replyList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(replyList));
    }

}