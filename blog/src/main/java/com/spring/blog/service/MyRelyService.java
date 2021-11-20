package com.spring.blog.service;

import com.spring.common.entity.MyRely;
import com.spring.common.model.RestMsg;

import java.util.List;

/**
 * (MyRely)表服务接口
 *
 * @author makejava
 * @since 2021 -11-13 11:54:04
 */
public interface MyRelyService {
    /**
     * Insert rest msg.
     *
     * @param myRely the my rely
     * @return the rest msg
     */
    RestMsg insert(MyRely myRely);

    /**
     * Update rest msg.
     *
     * @param myRely the my rely
     * @return the rest msg
     */
    RestMsg update(MyRely myRely);

    /**
     * Delete rest msg.
     *
     * @param replyIds the reply ids
     * @return the rest msg
     */
    RestMsg delete(List<Integer> replyIds);

    /**
     * Select rest msg.
     *
     * @param userId the user id
     * @return the rest msg
     */
    RestMsg select(Integer userId,int pageNum,int pageSize);
}