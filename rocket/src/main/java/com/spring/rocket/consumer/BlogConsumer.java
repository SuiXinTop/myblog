package com.spring.rocket.consumer;

import com.spring.common.entity.MyBlog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-17
 * @描述
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = "MyBlog", consumerGroup = "consumer-blog")
public class BlogConsumer implements RocketMQListener<MyBlog> {

    @Override
    public void onMessage(MyBlog myBlog) {
        log.info("Receive message：" + myBlog);
    }

}
