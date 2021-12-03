package com.spring.chat.controller;

import com.spring.chat.config.WebSocketConfig;
import com.spring.chat.config.WebSocketEncode;
import com.spring.chat.service.ChatGroupService;
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
 * ws://localhost:8004/group/{userId}
 *
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-27
 * @描述
 */
@Slf4j
@Component
@ServerEndpoint(value = "/group/{userId}", configurator = WebSocketConfig.class,encoders = WebSocketEncode.class)
public class WebSocketForGroup {

    private static ChatGroupService chatGroupService;

    @Autowired
    public void setOgLocationService(ChatGroupService chatGroupService) {
        WebSocketForGroup.chatGroupService = chatGroupService;
    }


    /**
     * 用来保存websocket连接信息，供统计查询当前连接数使用
     */
    private static Map<String, Session> SessionPool = new ConcurrentHashMap<>();
    private Session session;
    private User user;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 链接session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") Integer userId) {

        SessionPool.putIfAbsent(userId.toString(), session);
        this.session = session;

        this.user = new User();

        sendToFrom("连接成功");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 消息内容
     */
    @OnMessage
    public void onMessage(String message) {


        if (!SessionPool.containsKey(user.getUserId().toString())) {
            return;
        }
        push(message);
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
        SessionPool.remove(user.getUserId().toString());
        //然后关闭
        this.close();
    }

    public void push(String msg) {
        if (!session.isOpen()) {
            this.close();
            throw new RuntimeException("session is closed");
        }

        this.sendToOthers(msg);
    }

    /**
     * 推送消息给聊天室的其他人的客户端
     *
     * @param msg 消息内容
     */
    public void sendToOthers(String msg) {
        SessionPool.values().stream().filter(i -> !(i.getId().equals(this.session.getId()))).forEach(i -> {
            try {
                i.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 推送消息给自己的客户端
     *
     * @param msg 消息内容
     */
    public void sendToFrom(String msg) {
        try {
            session.getBasicRemote().sendText(msg);
        } catch (Exception e) {
            log.error("websocket连接发送客户端发送消息时异常！{}-{}", msg, this, e);
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
