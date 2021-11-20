package com.spring.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.MyBlogDao;
import com.spring.blog.dao.MyCommentDao;
import com.spring.blog.service.MyCommentService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.MyComment;
import com.spring.common.exception.ServiceException;
import com.spring.common.model.RestMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (MyComment)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:53:51
 */
@Service("myCommentService")
@RequiredArgsConstructor
public class MyCommentServiceImpl implements MyCommentService {
    private final MyCommentDao myCommentDao;
    private final MyBlogDao myBlogDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg insert(MyComment myComment) {
        myComment.setComTime(new DateTime());
        if (myCommentDao.insert(myComment) == 0) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }

        if (myBlogDao.addComment(myComment.getBlogId()) == 0) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }

        return RestMsg.success(MsgConstant.INSERT_SUCCESS, "");
    }

    @Override
    public RestMsg update(MyComment myComment) {
        if (myCommentDao.updateById(myComment) == 0) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, "");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg delete(List<Integer> comIds) {

        List<MyComment> myCommentList = myCommentDao.selectBatchIds(comIds);
        myCommentList.forEach(i -> {
            if (myBlogDao.subComment(i.getBlogId()) == 0) {
                throw new ServiceException(MsgConstant.DELETE_FAULT);
            }
        });

        if (myCommentDao.deleteBatchIds(comIds) == 0) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }

        return RestMsg.success(MsgConstant.DELETE_SUCCESS, "");
    }

    @Override
    public RestMsg select(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyComment> list = myCommentDao.selectByUserId(userId);
        if (list.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(list));
    }
}