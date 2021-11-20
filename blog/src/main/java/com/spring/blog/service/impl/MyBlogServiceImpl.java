package com.spring.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.MyBlogDao;
import com.spring.blog.dao.MyBlogTagDao;
import com.spring.blog.redis.RedisService;
import com.spring.blog.service.MyBlogService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.constant.RedisConstant;
import com.spring.common.constant.Topic;
import com.spring.common.entity.MyBlog;
import com.spring.common.entity.MyBlogTag;
import com.spring.common.entity.MyHistory;
import com.spring.common.exception.ServiceException;
import com.spring.common.model.RestMsg;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (MyBlog)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:53:17
 */
@Service("myBlogService")
@RequiredArgsConstructor
public class MyBlogServiceImpl implements MyBlogService {
    private final MyBlogDao myBlogDao;
    private final MyBlogTagDao myBlogTagDao;
    private final RedisService redisService;
    private final RocketMQTemplate rocketMqTemplate;

    @Override
    public RestMsg insert(MyBlog myBlog) {
        if (myBlogDao.insert(myBlog) == 0) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }
        return RestMsg.success(MsgConstant.INSERT_SUCCESS, "");
    }

    @Override
    public RestMsg update(MyBlog myBlog) {
        redisService.del(RedisConstant.BLOG_PREFIX + myBlog.getBlogId());
        if (myBlogDao.updateById(myBlog) == 0) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        redisService.setExpire(RedisConstant.BLOG_PREFIX + myBlog.getBlogId(), myBlog, RedisConstant.BLOG_EXPIRE_TIME);
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, "");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg delete(List<Integer> blogIds) {
        if (myBlogDao.deleteBatchIds(blogIds) == 0) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }
        blogIds.forEach(i -> redisService.del(RedisConstant.BLOG_PREFIX + i));
        return RestMsg.success(MsgConstant.DELETE_SUCCESS, "");
    }

    @Override
    public RestMsg select(Integer blogId) {
        MyBlog myBlog;
        if (redisService.hasKey(RedisConstant.BLOG_PREFIX + blogId)) {
            myBlog = (MyBlog) redisService.get(RedisConstant.BLOG_PREFIX + blogId);
            return RestMsg.success(MsgConstant.SELECT_SUCCESS, myBlog);
        }
        myBlog = myBlogDao.selectByBlogId(blogId);
        if (myBlog == null) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        redisService.setExpire(RedisConstant.BLOG_PREFIX + myBlog.getBlogId(), myBlog, RedisConstant.BLOG_EXPIRE_TIME);
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, myBlog);
    }

    @Override
    public RestMsg selectByHot() {
        if (redisService.hasKey(RedisConstant.BLOG_HOT)) {
            List<MyBlog> redis = (List<MyBlog>) redisService.get(RedisConstant.BLOG_HOT);
            return RestMsg.success(MsgConstant.SELECT_SUCCESS, redis);
        }
        List<MyBlog> list = myBlogDao.selectHot();
        redisService.setExpire(RedisConstant.BLOG_HOT, list, RedisConstant.HOT_EXPIRE_TIME);
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, list);
    }

    @Override
    public RestMsg selectNew(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyBlog> list = myBlogDao.selectHot();
        redisService.setExpire(RedisConstant.BLOG_HOT, list, RedisConstant.HOT_EXPIRE_TIME);
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(list));
    }

    @Override
    public void addView(MyHistory myHistory) {
        myHistory.setHistoryTime(new DateTime());
        rocketMqTemplate.sendOneWay(Topic.ADD_VIEW, myHistory);
    }

    @Override
    public void addLike(Integer blogId) {
        rocketMqTemplate.sendOneWay(Topic.ADD_LIKE, blogId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg insertTag(List<MyBlogTag> myBlogTagList) {
        if (myBlogTagList.isEmpty()) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }
        myBlogTagList.forEach(myBlogTag -> {
            if (myBlogTagDao.insert(myBlogTag) == 0) {
                throw new ServiceException(MsgConstant.INSERT_FAULT);
            }
        });
        return RestMsg.success(MsgConstant.INSERT_SUCCESS, "");
    }

    @Override
    public RestMsg deleteTag(List<Integer> blogTagIds) {
        if (blogTagIds.isEmpty()) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }
        if (myBlogTagDao.deleteBatchIds(blogTagIds) == 0) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }
        return RestMsg.success(MsgConstant.DELETE_SUCCESS, "");
    }

}