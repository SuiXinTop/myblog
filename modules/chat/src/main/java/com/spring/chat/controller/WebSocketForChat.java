package com.spring.chat.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONUtil;
import com.spring.chat.config.WebSocketConfig;
import com.spring.chat.service.ChatService;
import com.spring.common.entity.bo.ChatChannelMap;
import com.spring.common.entity.po.ChatMsg;
import com.spring.common.entity.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ws://localhost:8004/websocket/{userId}
 *
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-27
 * @描述
 */
@Slf4j
@Component
@ServerEndpoint(value = "/chat/{channelId}", configurator = WebSocketConfig.class)
public class WebSocketForChat {

    private static ChatService chatService;

    @Autowired
    public void setOgLocationService(ChatService chatService) {
        WebSocketForChat.chatService = chatService;
    }


    /**
     * 用来保存websocket连接信息，供统计查询当前连接数使用
     */
    private static final Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();
    private Session session;
    private Integer channelId;
    private Integer otherChannelId;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 链接session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "channelId") Integer channelId) {

        SESSION_MAP.putIfAbsent(channelId.toString(), session);
        this.session = session;

        ChatChannelMap chatChannelMap = chatService.getChannel(channelId);
        User fromUser = chatChannelMap.getFromUserMap();
        User toUser = chatChannelMap.getToUserMap();
        this.channelId = channelId;
        this.otherChannelId = chatService.getChannelId(fromUser.getUserId(), toUser.getUserId());

        sendToFrom("连接成功");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 消息内容
     */
    @OnMessage
    public void onMessage(String message) {
        ChatMsg chatMsg = ChatMsg.builder()
                .channelId(otherChannelId)
                .msgContent(message)
                .msgTime(new DateTime())
                .build();

        if (!SESSION_MAP.containsKey(channelId.toString())) {
            return;
        }

        //若对方不在线则不推送消息，直接存入mysql
        if (!SESSION_MAP.containsKey(otherChannelId.toString())) {
            chatMsg.setMsgStatus(0);
            chatService.insertMsg(chatMsg);
            return;
        }
        push(chatMsg);
        chatService.insertMsg(chatMsg);
    }

    /**
     * 发生异常时的处理
     *
     * @param session   客户端session
     * @param throwable session
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        if (this.session != null && this.session.isOpen()) {
            sendToFrom("错误");
            log.error("websocket连接onError。inputSession：{}-localSession：{}", session.getId(), this, throwable);
            this.close();
        } else {
            log.debug("已经关闭的websocket连接发生异常！inputSession：{}-localSession：{}", session.getId(), this, throwable);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.debug("websocket连接onClose。{}", this);
        SESSION_MAP.remove(channelId.toString());
        //然后关闭
        this.close();
    }

    public void push(ChatMsg chatMsg) {
        if (!session.isOpen()) {
            this.close();
            throw new RuntimeException("session is closed");
        }

        String msg = JSONUtil.toJsonStr(chatMsg);
        this.sendToOther(msg);
    }

    public void sendToOther(String msg) {
        try {
            SESSION_MAP.get(otherChannelId.toString()).getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToFrom(String msg) {
        sendMessage(msg);
    }

    /**
     * 推送消息给客户端
     *
     * @param message 消息内容
     */
    private void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("websocket连接发送客户端发送消息时异常！{}-{}", message, this, e);
        }
    }


    /**
     * 关闭session连接
     */
    private void close() {
        if (session == null) {
            log.debug("websocket连接关闭完成。{}", this);
            return;
        }
        try {
            if (session.isOpen()) {
                session.close();
            }
            log.info("连接已经关闭");
        } catch (IOException e) {
            log.error("websocket连接关闭时异常。{}", this, e);
        }
    }


}
