package com.spring.chat.controller;

import cn.hutool.core.date.DateTime;
import com.spring.chat.config.WebSocketConfig;
import com.spring.chat.config.WebSocketEncode;
import com.spring.chat.service.ChatGroupService;
import com.spring.common.entity.dto.WebSocketMsg;
import com.spring.common.entity.po.ChatGroup;
import com.spring.common.entity.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * ws://localhost:8004/group/{userId}
 *
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021 -11-27
 * @描述
 */
@Slf4j
@Component
@ServerEndpoint(value = "/group/{userId}", configurator = WebSocketConfig.class, encoders = WebSocketEncode.class)
public class WebSocketForGroup {

    private static ChatGroupService chatGroupService;

    /**
     * Sets og location service.
     *
     * @param chatGroupService the chat group service
     */
    @Autowired
    public void setOgLocationService(ChatGroupService chatGroupService) {
        WebSocketForGroup.chatGroupService = chatGroupService;
    }

    /**
     * 用来保存websocket连接信息，供统计查询当前连接数使用
     */
    public static Set<Session> SessionPool = new CopyOnWriteArraySet<>();
    /**
     * 用来存储，用户列表
     */
    public static Set<User> userSet = new CopyOnWriteArraySet<>();
    private Session session;
    private User user;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 链接session
     * @param userId  the user id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") Integer userId) {
        user = chatGroupService.getUser(userId);
        if (user == null) {
            return;
        }
        userSet.add(user);

        SessionPool.add(session);
        this.session = session;
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 消息内容
     */
    @OnMessage
    public void onMessage(String message) {
        if (!SessionPool.contains(session)) {
            return;
        }
        Date date =new DateTime();
        sendToAll(new WebSocketMsg(user, message, date));

        //存储群聊消息
        ChatGroup chatGroup = ChatGroup.builder()
                .userId(user.getUserId())
                .msgContent(message)
                .msgTime(date)
                .build();
        chatGroupService.insert(chatGroup);
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
            log.error("websocket连接onError。inputSession：{}-localSession：{}", session.getId(), this, throwable);
            close();
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
        close();
    }

    /**
     * 推送消息给聊天室的其他人的客户端
     *
     * @param webSocketMsg the web socket msg
     */
    public void sendToAll(WebSocketMsg webSocketMsg) {
        if (!session.isOpen()) {
            close();
            throw new RuntimeException("session is closed");
        }
        SessionPool.forEach(i -> i.getAsyncRemote().sendObject(webSocketMsg));
    }

    /**
     * 推送消息给自己的客户端
     *
     * @param msg 消息内容
     */
    public void sendToFrom(String msg) {
        try {
            session.getAsyncRemote().sendText(msg);
        } catch (Exception e) {
            log.error("websocket连接发送客户端发送消息时异常！{}-{}", msg, this, e);
        }
    }

    /**
     * 关闭session连接
     */
    private void close() {
        userSet.remove(user);
        SessionPool.remove(session);
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
