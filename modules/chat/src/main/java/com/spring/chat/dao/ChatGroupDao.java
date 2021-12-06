package com.spring.chat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.po.ChatGroup;
import com.spring.common.entity.po.User;
import io.lettuce.core.dynamic.annotation.Param;

/**
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
}
