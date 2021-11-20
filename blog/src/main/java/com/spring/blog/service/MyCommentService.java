package com.spring.blog.service;

import com.spring.common.entity.MyComment;
import com.spring.common.model.RestMsg;

import java.util.List;

/**
 * (MyComment)表服务接口
 *
 * @author makejava
 * @since 2021 -11-13 11:53:51
 */
public interface MyCommentService {

    /**
     * Insert rest msg.
     *
     * @param myComment the my comment
     * @return the rest msg
     */
    RestMsg insert(MyComment myComment);

    /**
     * Update rest msg.
     *
     * @param myComment the my comment
     * @return the rest msg
     */
    RestMsg update(MyComment myComment);

    /**
     * Delete rest msg.
     *
     * @param comIds the com ids
     * @return the rest msg
     */
    RestMsg delete(List<Integer> comIds);

    /**
     * Select rest msg.
     *
     * @param userId   the user id
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the rest msg
     */
    RestMsg select(Integer userId,int pageNum,int pageSize);
}