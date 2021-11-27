package com.spring.chat.controller;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-27
 * @描述
 */
@Component
@ServerEndpoint(value = "/websocket/{userId}")
public class WebSocket {
    private static Map<String, WebSocket> webSocketSet = new ConcurrentHashMap<>();
    private static int onlineCount = 0;

    @OnOpen
    public void onOpen(EndpointConfig config) {

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {


    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {

    }


    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

}
