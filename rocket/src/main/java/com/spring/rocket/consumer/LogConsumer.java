package com.spring.rocket.consumer;

import com.spring.common.entity.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-09
 * @描述
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "SysOperLog",consumerGroup = "consumer-log")
public class LogConsumer implements RocketMQListener<SysLog>, RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(SysLog sysLog) {
        log.info("Receive message：" + sysLog);
        //如果消费失败，则抛出RuntimeException，RocketMQ会自动重试
        //可以手动抛出，也可以使用 Lombok 的 @SneakyThrows 注解来抛出 RuntimeException
    }
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // 每次拉取的间隔，单位为毫秒
        consumer.setPullInterval(10000);
        consumer.setConsumeThreadMin(2);
        consumer.setConsumeThreadMax(4);
        // 设置每次从队列中拉取的消息数为16
        consumer.setPullBatchSize(20);
        consumer.setConsumeMessageBatchMaxSize(10);
    }
}

