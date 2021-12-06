package com.spring.chat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.po.ChatChannel;
import com.spring.common.entity.vo.ChatChannelVo;

import java.util.List;

/**
 * The interface Chat channel dao.
 *
 * @author STARS
 */
public interface ChatChannelDao extends BaseMapper<ChatChannel> {
    /**
     * Gets channel list.
     *
     * @param userId the user id
     * @return the channel list
     */
    List<ChatChannelVo> getChannelList(Integer userId);

    /**
     * Gets channel.
     *
     * @param channelId the channel id
     * @return the channel
     */
    ChatChannelVo getChannel(Integer channelId);

    /**
     * Gets channel id.
     *
     * @param chatChannel the chat channel
     * @return the channel id
     */
    Integer getChannelId(ChatChannel chatChannel);

    /**
     * Has channel int.
     *
     * @param chatChannel the chat channel
     * @return the int
     */
    int hasChannel(ChatChannel chatChannel);

}
