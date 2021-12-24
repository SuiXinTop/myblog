package com.spring.file.task;

import com.spring.common.constant.Topic;
import com.spring.file.dao.BlogDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-19
 * @描述
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = Topic.ADD_LIKE, consumerGroup = "consumer-like")
public class LikeConsumer implements RocketMQListener<Integer>, RocketMQPushConsumerLifecycleListener {
    private final BlogDao blogDao;

    @Override
    public void onMessage(Integer blogId) {
        blogDao.addLike(blogId);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // 每次拉取的间隔，单位为毫秒
        consumer.setPullInterval(17000);
        consumer.setConsumeThreadMin(2);
        consumer.setConsumeThreadMax(4);
        // 设置每次从队列中拉取的消息数为16
        consumer.setPullBatchSize(100);
        consumer.setConsumeMessageBatchMaxSize(100);
    }
}
