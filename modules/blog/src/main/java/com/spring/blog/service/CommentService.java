package com.spring.blog.service;

import com.spring.common.entity.po.Comment;
import com.spring.common.entity.dto.RestMsg;

import java.util.List;

/**
 * (MyComment)表服务接口
 *
 * @author makejava
 * @since 2021 -11-13 11:53:51
 */
public interface CommentService {

    /**
     * Insert rest msg.
     *
     * @param comment the my comment
     * @return the rest msg
     */
    RestMsg insert(Comment comment);

    /**
     * Update rest msg.
     *
     * @param comment the my comment
     * @return the rest msg
     */
    RestMsg update(Comment comment);

    /**
     * Delete rest msg.
     *
     * @param comIds the com ids
     * @return the rest msg
     */
    RestMsg delete(List<Integer> comIds);

    /**
     * Select by blog id rest msg.
     *
     * @param blogId   the blog id
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the rest msg
     */
    RestMsg selectByBlogId(Integer blogId,int pageNum,int pageSize);

    /**
     * Select rest msg.
     *
     * @param userId   the user id
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the rest msg
     */
    RestMsg selectByUserId(Integer userId,int pageNum,int pageSize);
}