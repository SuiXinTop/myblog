package com.spring.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.BlogDao;
import com.spring.blog.dao.BlogTagDao;
import com.spring.blog.service.BlogService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.constant.RedisConstant;
import com.spring.common.constant.Topic;
import com.spring.common.enmu.Status;
import com.spring.common.entity.vo.BlogVo;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.po.Blog;
import com.spring.common.entity.po.BlogTag;
import com.spring.common.entity.po.History;
import com.spring.common.exception.ServiceException;
import com.spring.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * (MyBlog)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:53:17
 */
@Service("myBlogService")
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogDao blogDao;
    private final BlogTagDao blogTagDao;
    private final RedisService redisService;
    private final RocketMQTemplate rocketMQTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg insert(Blog blog) {
        Date now = new DateTime();
        blog.setBlogUpdateTime(now);
        blog.setBlogTime(now);

        if (blogDao.insert(blog) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }
        return RestMsg.success(MsgConstant.INSERT_SUCCESS, blog.getBlogId());
    }

    @Override
    public RestMsg saveTemp(Blog blog) {
        Date now = new DateTime();
        blog.setBlogUpdateTime(now);
        blog.setBlogTime(now);
        if (!redisService.set(RedisConstant.BLOG_TEMP + blog.getUserId(), blog)) {
            return RestMsg.fail(MsgConstant.INSERT_FAULT);
        }
        return RestMsg.success(MsgConstant.INSERT_SUCCESS, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg update(Blog blog) {
        redisService.del(RedisConstant.BLOG_PREFIX + blog.getBlogId());

        blog.setBlogUpdateTime(new DateTime());
        if (blogDao.updateById(blog) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        redisService.del(RedisConstant.BLOG_PREFIX + blog.getBlogId());
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg recoverBlog(List<Integer> blogIdList) {
        if (blogIdList.isEmpty()) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        blogIdList.forEach(blogId -> {
            if (blogDao.updateBlogState(blogId) == Status.Exception.ordinal()) {
                throw new ServiceException(MsgConstant.UPDATE_FAULT);
            }
        });
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg delete(List<Integer> blogIds) {
        if (blogDao.deleteBatchIds(blogIds) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }

        Blog update = Blog.builder().blogUpdateTime(new DateTime()).build();
        blogIds.forEach(i -> {
            update.setBlogId(i);
            blogDao.updateById(update);
        });

        blogIds.forEach(i -> redisService.del(RedisConstant.BLOG_PREFIX + i));
        return RestMsg.success(MsgConstant.DELETE_SUCCESS, null);
    }

    @Override
    public RestMsg select(Integer blogId) {
        BlogVo blog;
        if (redisService.hasKey(RedisConstant.BLOG_PREFIX + blogId)) {
            blog = (BlogVo) redisService.get(RedisConstant.BLOG_PREFIX + blogId);
            return RestMsg.success(MsgConstant.SELECT_SUCCESS, blog);
        }
        blog = blogDao.selectByBlogId(blogId);
        if (blog == null) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        redisService.setExpire(RedisConstant.BLOG_PREFIX + blog.getBlogId(), blog, RedisConstant.BLOG_EXPIRE_TIME);

        return RestMsg.success(MsgConstant.SELECT_SUCCESS, blog);
    }

    @Override
    public RestMsg getTemp(Integer userId) {
        Blog blog = (Blog) redisService.get(RedisConstant.BLOG_TEMP+userId);
        if (blog==null){
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(blog);
    }

    @Override
    public RestMsg selectBlogList(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BlogVo> blogList = blogDao.selectByUserId(userId);
        if (blogList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(blogList));
    }

    @Override
    public  RestMsg selectNormal(int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<BlogVo> blogList = blogDao.selectNormal();
        if (blogList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(blogList));
    }

    @Override
    public RestMsg selectException(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BlogVo> blogList = blogDao.selectException();
        if (blogList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(blogList));
    }

    @Override
    public RestMsg selectHot() {
        if (redisService.hasKey(RedisConstant.BLOG_HOT)) {
            List<BlogVo> redis = (List<BlogVo>) redisService.get(RedisConstant.BLOG_HOT);
            return RestMsg.success(MsgConstant.SELECT_SUCCESS, redis);
        }
        List<BlogVo> blogList = blogDao.selectHot();
        if(blogList.isEmpty()){
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        redisService.setExpire(RedisConstant.BLOG_HOT, blogList, RedisConstant.HOT_EXPIRE_TIME);
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, blogList);
    }

    @Override
    public RestMsg selectNew() {
        if (redisService.hasKey(RedisConstant.BLOG_NEW)) {
            List<BlogVo> redis = (List<BlogVo>) redisService.get(RedisConstant.BLOG_NEW);
            return RestMsg.success(MsgConstant.SELECT_SUCCESS, redis);
        }
        List<BlogVo> blogList = blogDao.selectNew();
        if (blogList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        redisService.setExpire(RedisConstant.BLOG_NEW, blogList, RedisConstant.NEW_EXPIRE_TIME);
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, blogList);
    }

    @Override
    public RestMsg selectNewByUserId(Integer userId){
        List<Blog> blogList = blogDao.selectNewByUserId(userId);
        return RestMsg.success(blogList);
    }

    @Override
    public void addView(History history) {
        history.setHistoryTime(new DateTime());
        rocketMQTemplate.sendOneWay(Topic.ADD_VIEW, history);
    }

    @Override
    public void addLike(Integer blogId) {
        rocketMQTemplate.sendOneWay(Topic.ADD_LIKE, blogId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg insertTag(List<Integer> tagIdList,Integer blogId) {
        if (tagIdList.isEmpty()) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }
        tagIdList.forEach(id -> {
            BlogTag blogTag = new BlogTag();
            blogTag.setBlogId(blogId);
            blogTag.setTagId(id);
            if (blogTagDao.insert(blogTag) == Status.Exception.ordinal()) {
                throw new ServiceException(MsgConstant.INSERT_FAULT);
            }
        });

        Blog blog = Blog.builder().blogId(blogId).blogUpdateTime(new DateTime()).build();
        if (blogDao.updateById(blog) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }
        return RestMsg.success(MsgConstant.INSERT_SUCCESS, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg deleteTag(List<Integer> blogTagIdList, Integer blogId) {
        if (blogTagIdList.isEmpty()) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }

        if (blogTagDao.deleteBatchIds(blogTagIdList) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }

        Blog blog = Blog.builder().blogId(blogId).blogUpdateTime(new DateTime()).build();
        if (blogDao.updateById(blog) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }

        return RestMsg.success(MsgConstant.DELETE_SUCCESS, null);
    }

}