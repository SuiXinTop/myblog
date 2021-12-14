package com.spring.chat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.po.ChatGroup;
import com.spring.common.entity.po.User;
import com.spring.common.entity.vo.ChatGroupVo;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * The interface Chat group dao.
 *
 * @author STARS
 */
public interface ChatGroupDao extends BaseMapper<ChatGroup> {

    /**
     * Gets user.
     *
     * @param userId the user id
     * @return the user
     */
    User getUser(@Param(value = "user_id")Integer userId);

    /**
     * Gets history msg.
     *
     * @return the history msg
     */
    List<ChatGroupVo> getHistoryMsg();
}
