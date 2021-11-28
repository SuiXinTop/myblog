package com.spring.chat.controller;

import cn.hutool.core.util.StrUtil;
import com.spring.chat.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
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
@ServerEndpoint(value = "/group/{userId}",configurator = ServerConfig.class)
public class WebSocket {
    /**
     * 用来保存websocket连接信息，供统计查询当前连接数使用
     */
    private static  Map<String,Session> sessionMap =new ConcurrentHashMap<>();
    private static  Map<String, WebSocket> userClientMap = new ConcurrentHashMap<>();
    private Session session;
    private Integer userId;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 链接session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") Integer userId) {


        sessionMap.put(userId.toString(),session);
        userClientMap.put(session.getId(), this);
        this.session = session;
        this.userId=userId;
        log.info("userId:{}",userId);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 消息内容
     * @param session session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.debug("websocket连接onMessage。{},{},{}", message, this.toString(), session.getId());
        String key = this.session.getId();
        if (!userClientMap.containsKey(key)) {
            this.close();
            return;
        }
        push("token",  message);
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
            log.error("websocket连接onError。inputSession：{}-localSession：{}", session.getId(), this.toString(), throwable);
            this.close();
        } else {
            log.debug("已经关闭的websocket连接发生异常！inputSession：{}-localSession：{}", session.getId(), this.toString(), throwable);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.debug("websocket连接onClose。{}", this.toString());
        sessionMap.remove(this.userId.toString());
        //然后关闭
        this.close();
    }

    public void push(String token, String message) {
        if (StrUtil.isEmpty(token)) {
            throw new RuntimeException("token must not be null. token=" + token + ", message=" + message);
        }

        if (StrUtil.isEmpty(message)) {
            throw new RuntimeException("message must not be null. token=" + token + ", message=" + message);
        }

        if (!session.isOpen()) {
            this.close();
            throw new RuntimeException("session is closed. token=" + token + ", message=" + message);
        }

        //发送消息
        this.sendToAll(message);

    }

    /**
     * 推送消息给客户端
     *
     * @param message 消息内容
     */
    private void sendMessage(String message) {
        try {
            //使用同步块和同步方法发送。看是否能防止产生IllegalStateException异常
            //modify by caoshuo at 200506
                session.getBasicRemote().sendText(message);
                log.info(message);
        } catch (Exception e) {
            log.error("websocket连接发送客户端发送消息时异常！{}-{}", message, this.toString(), e);
        }
    }

    /**
     * 关闭session连接
     */
    private void close() {
        //从本机map中移除连接信息
        userClientMap.remove(this.session.getId());
        if (session == null) {
            log.debug("websocket连接关闭完成。{}", this.toString());
            return;
        }

        try {
            if (session.isOpen()) {
                session.close();
            }
            log.info("连接已经关闭");
        } catch (IOException e) {
            log.error("websocket连接关闭时异常。{}", this.toString(), e);
        }
    }

    public void sendToAll(String str) {
        userClientMap.values().forEach(item -> {
            item.sendMessage(str);
        });
//        userClientMap.values().stream().filter(i -> i.userId.equals(1)).forEach(i -> i.sendMessage(str));
    }
}
