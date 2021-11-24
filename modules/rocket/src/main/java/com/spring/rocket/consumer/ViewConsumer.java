package com.spring.rocket.consumer;

import com.spring.common.constant.Topic;
import com.spring.common.entity.po.History;
import com.spring.rocket.dao.BlogDao;
import com.spring.rocket.dao.HistoryDao;
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
@RocketMQMessageListener(topic = Topic.ADD_VIEW, consumerGroup = "consumer-view")
public class ViewConsumer implements RocketMQListener<History>, RocketMQPushConsumerLifecycleListener {
    private final BlogDao blogDao;
    private final HistoryDao historyDao;

    @Override
    public void onMessage(History history) {
        blogDao.addView(history.getBlogId());
        historyDao.insert(history);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // 每次拉取的间隔，单位为毫秒
        consumer.setPullInterval(20000);
        consumer.setConsumeThreadMin(2);
        consumer.setConsumeThreadMax(4);
        // 设置每次从队列中拉取的消息数为16
        consumer.setPullBatchSize(100);
        consumer.setConsumeMessageBatchMaxSize(100);
    }
}
