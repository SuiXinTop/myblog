package com.spring.chat.service;

import cn.hutool.core.date.DateTime;
import com.spring.chat.dao.ChatChannelDao;
import com.spring.chat.dao.ChatMsgDao;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.dto.WebSocketMsg;
import com.spring.common.entity.po.ChatChannel;
import com.spring.common.entity.po.ChatMsg;
import com.spring.common.entity.vo.ChatChannelVo;
import com.spring.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Chat service.
 *
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021 -11-29
 * @描述
 */
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatChannelDao chatChannelDao;
    private final ChatMsgDao chatMsgDao;
    private final RedisTemplate redisTemplate;

    /**
     * 获取聊天列表
     *
     * @param userId the user id
     * @return channel list
     */
    public RestMsg getChannelList(Integer userId) {
        List<ChatChannelVo> channelMapList = chatChannelDao.getChannelList(userId);
        if (channelMapList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, channelMapList);
    }

    /**
     * 创建聊天channel
     *
     * @param fromUser the from user
     * @param toUser   the to user
     * @return the rest msg
     */
    @Transactional(rollbackFor = Exception.class)
    public RestMsg createChannel(Integer fromUser, Integer toUser) {
        ChatChannel chatChannel = ChatChannel.builder().toUser(toUser).fromUser(fromUser).build();
        if (chatChannelDao.hasChannel(chatChannel) == 0) {
            chatChannelDao.insert(chatChannel);
            ChatChannel other = ChatChannel.builder().toUser(fromUser).fromUser(toUser).build();
            chatChannelDao.insert(other);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, null);
    }

    /**
     * 获取聊天channel的信息
     *
     * @param channelId the channel id
     * @return the channel
     */
    public ChatChannelVo getChannel(Integer channelId) {
        return chatChannelDao.getChannel(channelId);
    }

    /**
     * 获取聊天channel的信息
     *
     * @param fromUser the from user
     * @param toUser   the to user
     * @return the channel id
     */
    public Integer getChannelId(Integer fromUser, Integer toUser) {
        ChatChannel chatChannel = ChatChannel.builder().fromUser(fromUser).toUser(toUser).build();
        return chatChannelDao.getChannelId(chatChannel);
    }

    /**
     * 上线时获取离线消息
     *
     * @param channelId the channel id
     * @return the offline msg
     */
    public RestMsg getOfflineMsg(Integer channelId) {
        List<WebSocketMsg> msgList = redisTemplate.opsForList().range("channel:" + channelId, 0, -1);
        if (msgList == null) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, msgList);
    }

    /**
     * 确认收到后，删除redis中的离线消息
     *
     * @param channelId the channel id
     */
    public void deleteOfflineMsg(Integer channelId) {
        redisTemplate.delete("channel:" + channelId);
    }

    public void updateOfflineMsg(Integer channelId) {
        chatMsgDao.updateOfflineMsgStatus(channelId);
    }

    /**
     * 修改channel，最后聊天时间
     *
     * @param channelId the channel id
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateLastTime(Integer channelId) {
        ChatChannel update = ChatChannel.builder().channelId(channelId).lastChatTime(new DateTime()).build();
        chatChannelDao.updateById(update);
    }

    /**
     * 聊天记录持久化
     *
     * @param chatMsg the chat msg
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertMsg(ChatMsg chatMsg) {
        chatMsgDao.insert(chatMsg);
    }

}
