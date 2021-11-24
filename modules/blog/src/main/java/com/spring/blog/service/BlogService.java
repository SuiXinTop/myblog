package com.spring.blog.service;

import com.spring.common.entity.po.Blog;
import com.spring.common.entity.po.BlogTag;
import com.spring.common.entity.po.History;
import com.spring.common.entity.dto.RestMsg;

import java.util.List;

/**
 * (MyBlog)表服务接口
 *
 * @author makejava
 * @since 2021 -11-13 11:53:17
 */
public interface BlogService {
    /**
     * Insert rest msg.
     *
     * @param blog the my blog
     * @return the rest msg
     */
    RestMsg insert(Blog blog);

    /**
     * Update rest msg.
     *
     * @param blog the my blog
     * @return the rest msg
     */
    RestMsg update(Blog blog);

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
    RestMsg selectHot();

    /**
     * Select new rest msg.
     *
     * @return the rest msg
     */
    RestMsg selectNew();

    /**
     * Add view.
     *
     * @param history the my history
     */
    void addView(History history);

    /**
     * Add like.
     *
     * @param blogId the blog id
     */
    void addLike(Integer blogId);

    /**
     * Insert tag rest msg.
     *
     * @param blogTagList the my blog tag list
     * @return the rest msg
     */
    RestMsg insertTag(List<BlogTag> blogTagList);

    /**
     * Delete tag rest msg.
     *
     * @param blogTagIds the blog tag ids
     * @param blogId     the blog id
     * @return the rest msg
     */
    RestMsg deleteTag(List<Integer> blogTagIds,Integer blogId);
}