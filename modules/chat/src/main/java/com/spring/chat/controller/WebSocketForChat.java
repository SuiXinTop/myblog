package com.spring.chat.controller;

import cn.hutool.core.date.DateTime;
import com.spring.chat.config.WebSocketConfig;
import com.spring.chat.config.WebSocketEncode;
import com.spring.chat.service.ChatService;
import com.spring.common.entity.dto.WebSocketMsg;
import com.spring.common.entity.po.ChatMsg;
import com.spring.common.entity.po.User;
import com.spring.common.entity.vo.ChatChannelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ws://localhost:8004/chat/{channelId}
 *
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021 -11-27
 * @描述
 */
@Slf4j
@Component
@ServerEndpoint(value = "/chat/{channelId}", configurator = WebSocketConfig.class, encoders = WebSocketEncode.class)
public class WebSocketForChat {

    private static ChatService chatService;

    /**
     * Sets og location service.
     *
     * @param chatService the chat service
     */
    @Autowired
    public void setOgLocationService(ChatService chatService) {
        WebSocketForChat.chatService = chatService;
    }


    /**
     * 用来保存websocket连接信息，供统计查询当前连接数使用
     */
    private static Map<String, WebSocketForChat> webSocketPool = new ConcurrentHashMap<>();
    private Session session;

    private Integer channelId;
    private User fromUser;

    private Integer otherChannelId;
    private User toUser;


    /**
     * 连接建立成功调用的方法
     *
     * @param session   链接session
     * @param channelId the channel id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "channelId") Integer channelId) {
        webSocketPool.put(channelId.toString(), this);
        this.session = session;
        this.channelId = channelId;

        ChatChannelVo chatChannelVo = chatService.getChannel(channelId);
        fromUser = chatChannelVo.getFromUserMap();
        toUser = chatChannelVo.getToUserMap();

        this.otherChannelId = chatService.getChannelId(fromUser.getUserId(), toUser.getUserId());

        List<ChatMsg> msgList = chatService.getLastMsg(channelId);
        if (!msgList.isEmpty()) {
            msgList.forEach(msg -> sendToFrom(new WebSocketMsg(toUser, msg.getMsgContent(), msg.getMsgTime())));
        }
        log.info("连接成功:{}", channelId);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 消息内容
     */
    @OnMessage
    public void onMessage(String message) {
        if (!webSocketPool.containsKey(channelId.toString())) {
            return;
        }

        ChatMsg chatMsg = ChatMsg.builder()
                .channelId(otherChannelId)
                .msgContent(message)
                .msgTime(new DateTime())
                .build();

        if (!webSocketPool.containsKey(otherChannelId.toString())) {
            chatMsg.setMsgStatus(0);
            chatService.insertMsg(chatMsg);
            return;
        }

        sendToOther(new WebSocketMsg(toUser, message, new DateTime()));
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
            sendToFrom(new WebSocketMsg(fromUser, "传输错误", new DateTime()));
            log.error("websocket连接onError。inputSession：{}-localSession：{}", session.getId(), this, throwable);
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
        //然后关闭
        close();
    }

    /**
     * Send to others.
     *
     * @param webSocketMsg the web socket msg
     */
    public void sendToOther(WebSocketMsg webSocketMsg) {
        if (!session.isOpen()) {
            close();
            throw new RuntimeException("session is closed");
        }
        webSocketPool.get(otherChannelId.toString()).sendMessage(webSocketMsg);
    }

    /**
     * Send to from.
     *
     * @param webSocketMsg the web socket msg
     */
    public void sendToFrom(WebSocketMsg webSocketMsg) {
        if (!session.isOpen()) {
            close();
            throw new RuntimeException("session is closed");
        }
        sendMessage(webSocketMsg);
    }

    /**
     * 推送消息给客户端
     */
    private void sendMessage(WebSocketMsg webSocketMsg) {
        try {
            session.getAsyncRemote().sendObject(webSocketMsg);
        } catch (Exception e) {
            log.error("websocket连接发送客户端发送消息时异常！");
        }
    }


    /**
     * 关闭session连接
     */
    private void close() {
        webSocketPool.remove(channelId.toString());
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
