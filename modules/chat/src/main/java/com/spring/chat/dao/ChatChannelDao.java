package com.spring.chat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.bo.ChatChannelMap;
import com.spring.common.entity.po.ChatChannel;

import java.util.List;

/**
 * @author STARS
 */
public interface ChatChannelDao extends BaseMapper<ChatChannel> {
    List<ChatChannelMap> getChannelList(Integer userId);

    ChatChannelMap getChannel(Integer channelId);

    Integer getChannelId(ChatChannel chatChannel);

    int hasChannel(ChatChannel chatChannel);
}
