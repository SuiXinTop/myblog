package com.spring.chat.service;

import com.spring.chat.dao.ChatChannelDao;
import com.spring.chat.dao.ChatMsgDao;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.bo.ChatChannelMap;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.po.ChatChannel;
import com.spring.common.entity.po.ChatMsg;
import com.spring.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-29
 * @描述
 */
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatChannelDao chatChannelDao;
    private final ChatMsgDao chatMsgDao;

    public RestMsg getChannelList(Integer userId) {
        List<ChatChannelMap> channelMapList = chatChannelDao.getChannelList(userId);
        if (channelMapList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, channelMapList);
    }

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

    public ChatChannelMap getChannel(Integer channelId) {
        return chatChannelDao.getChannel(channelId);
    }

    public Integer getChannelId(Integer fromUser, Integer toUser) {
        ChatChannel chatChannel = ChatChannel.builder().fromUser(fromUser).toUser(toUser).build();
        return chatChannelDao.getChannelId(chatChannel);
    }

    public RestMsg getLastMsg(Integer channelId) {
        List<ChatMsg> chatMsgList = chatMsgDao.getLastMsg(channelId);
        if (chatMsgList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }

        ChatMsg update = ChatMsg.builder().msgStatus(1).build();
        chatMsgList.forEach(i -> {
            update.setMsgId(i.getMsgId());
            chatMsgDao.updateById(update);
        });
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, chatMsgList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertMsg(ChatMsg chatMsg) {
        chatMsgDao.insert(chatMsg);
    }
}
