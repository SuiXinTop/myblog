package com.spring.blog.service;

import com.spring.common.entity.MyBlog;
import com.spring.common.entity.MyBlogTag;
import com.spring.common.entity.MyHistory;
import com.spring.common.model.RestMsg;

import java.util.List;

/**
 * (MyBlog)表服务接口
 *
 * @author makejava
 * @since 2021 -11-13 11:53:17
 */
public interface MyBlogService {
    /**
     * Insert rest msg.
     *
     * @param myBlog the my blog
     * @return the rest msg
     */
    RestMsg insert(MyBlog myBlog);

    /**
     * Update rest msg.
     *
     * @param myBlog the my blog
     * @return the rest msg
     */
    RestMsg update(MyBlog myBlog);

    /**
     * Delete rest msg.
     *
     * @param blogIds the blog ids
     * @return the rest msg
     */
    RestMsg delete(List<Integer> blogIds);

    /**
     * Select rest msg.
     *
     * @param blogId the blog id
     * @return the rest msg
     */
    RestMsg select(Integer blogId);

    /**
     * Select by hot rest msg.
     *
     * @return the rest msg
     */
    RestMsg selectByHot();

    /**
     * Select new rest msg.
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the rest msg
     */
    RestMsg selectNew(int pageNum, int pageSize);

    /**
     * Add view.
     *
     * @param myHistory the my history
     */
    void addView(MyHistory myHistory);

    /**
     * Add like.
     *
     * @param blogId the blog id
     */
    void addLike(Integer blogId);

    /**
     * Insert tag rest msg.
     *
     * @param myBlogTagList the my blog tag list
     * @return the rest msg
     */
    RestMsg insertTag(List<MyBlogTag> myBlogTagList);

    /**
     * Delete tag rest msg.
     *
     * @param blogTagIds the blog tag ids
     * @return the rest msg
     */
    RestMsg deleteTag(List<Integer> blogTagIds);
}