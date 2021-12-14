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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
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
    public void setChatService(ChatService chatService) {
        WebSocketForChat.chatService = chatService;
    }

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisService(RedisTemplate redisTemplate) {
        WebSocketForChat.redisTemplate = redisTemplate;
    }

    /**
     * 用来保存websocket连接信息，供统计查询当前连接数使用
     */
    private static Map<String, WebSocketForChat> webSocketPool = new ConcurrentHashMap<>();
    private Session session;

    /**
     * 自己的消息队列
     */
    private Integer channelId;
    /**
     * 对方的消息队列
     */
    private Integer otherChannelId;
    /**
     * 自己的信息
     */
    private User user;

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
        user = chatChannelVo.getFromUserMap();
        User toUser = chatChannelVo.getToUserMap();

        this.otherChannelId = chatService.getChannelId(user.getUserId(), toUser.getUserId());

//        //发送未读聊天记录
//        List<WebSocketMsg> msgList = redisTemplate.opsForList().range("channel:" + channelId, 0, -1);
//        if (msgList == null) {
//            return;
//        }
//
//        //以对方的信息发送给连接方
//        msgList.forEach(this::sendToFrom);
//
//        //删除离线消息
//        redisTemplate.delete("channel:"+channelId);

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 消息内容
     */
    @OnMessage
    public void onMessage(String message) {
        //自己不在线
        if (!webSocketPool.containsKey(channelId.toString())) {
            return;
        }

        Date date = new Date();
        //创建存储对象,发给对方的消息队列
        ChatMsg chatMsg = ChatMsg.builder()
                .channelId(otherChannelId)
                .msgContent(message)
                .msgTime(date)
                .build();

        WebSocketMsg webSocketMsg = new WebSocketMsg(user, message, date);
        //如果对方不在线，则不发送给对方，只转发给自己
        if (!webSocketPool.containsKey(otherChannelId.toString())) {
            chatMsg.setMsgStatus(0);
            redisTemplate.opsForList().rightPush("channel:" + otherChannelId,(Object) webSocketMsg);
            sendToFrom(webSocketMsg);
        } else {
            //如果对方在线，则发送给双方
            sendToAll(webSocketMsg);
        }

        //存储记录
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
            sendToFrom(new WebSocketMsg(user, "传输错误", new DateTime()));
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

    public void sendToAll(WebSocketMsg webSocketMsg) {
        sendToOther(webSocketMsg);
        sendToFrom(webSocketMsg);
    }

    /**
     * Send to others.
     *
     * @param webSocketMsg the web socket msg
     */
    public void sendToOther(WebSocketMsg webSocketMsg) {
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

    public static Map<String, WebSocketForChat> getWebSocketPool(){
        return webSocketPool;
    }

    public Integer getOtherChannelId(){
        return this.otherChannelId;
    }

}
