package com.spring.chat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.po.ChatMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author STARS
 */
public interface ChatMsgDao extends BaseMapper<ChatMsg> {

    /**
     * Update offline msg status.
     *
     * @param channelId the channel id
     */
    void updateOfflineMsgStatus(Integer channelId);

    /**
     * Gets history msg.
     *
     * @param channelOne the channel one
     * @param channelTwo the channel two
     * @return the history msg
     */
    List<ChatMsg> getHistoryMsg(@Param("channelOne")Integer channelOne,@Param("channelTwo")Integer channelTwo);
}
