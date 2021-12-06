package com.spring.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.BlogDao;
import com.spring.blog.dao.CommentDao;
import com.spring.blog.service.CommentService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.enmu.Status;
import com.spring.common.entity.vo.CommentVo;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.po.Comment;
import com.spring.common.exception.ServiceException;
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
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final BlogDao blogDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg insert(Comment comment) {
        comment.setComTime(new DateTime());
        if (commentDao.insert(comment) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }

        if (blogDao.addComment(comment.getBlogId()) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }

        return RestMsg.success(MsgConstant.INSERT_SUCCESS, "");
    }

    @Override
    public RestMsg update(Comment comment) {
        if (commentDao.updateById(comment) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg delete(List<Integer> comIds) {

        List<Comment> commentList = commentDao.selectBatchIds(comIds);
        commentList.forEach(i -> {
            if (blogDao.subComment(i.getBlogId()) == Status.Exception.ordinal()) {
                throw new ServiceException(MsgConstant.DELETE_FAULT);
            }
        });

        if (commentDao.deleteBatchIds(comIds) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }

        return RestMsg.success(MsgConstant.DELETE_SUCCESS, null);
    }

    @Override
    public RestMsg selectByBlogId(Integer blogId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommentVo> commentList = commentDao.selectAllByBlogId(blogId);
        if (commentList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(commentList));
    }

    @Override
    public RestMsg selectByUserId(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommentVo> commentList = commentDao.selectAllByUserId(userId);
        if (commentList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(commentList));
    }
}