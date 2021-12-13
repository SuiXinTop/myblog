package com.spring.chat.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.spring.common.entity.dto.WebSocketMsg;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-12-03
 * @描述
 */
public class WebSocketEncode implements Encoder.Text<WebSocketMsg> {

    @Override
    public void init(EndpointConfig arg0) {
    }

    @Override
    public String encode(WebSocketMsg webSocketMsg) {
        try {
            return JSON.toJSONString(webSocketMsg);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void destroy() {
    }
}
