package com.spring.blog.service;

import com.spring.common.entity.po.Reply;
import com.spring.common.entity.dto.RestMsg;

import java.util.List;

/**
 * (MyRely)表服务接口
 *
 * @author makejava
 * @since 2021 -11-13 11:54:04
 */
public interface ReplyService {
    /**
     * Insert rest msg.
     *
     * @param reply the my rely
     * @return the rest msg
     */
    RestMsg insert(Reply reply);

    /**
     * Update rest msg.
     *
     * @param reply the my rely
     * @return the rest msg
     */
    RestMsg update(Reply reply);

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