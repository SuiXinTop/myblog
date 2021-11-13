package com.spring.myblog.consumer;

import com.spring.myblog.entity.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-09
 * @描述
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "SysLog",consumerGroup = "consumer-log")
public class LogConsumer implements RocketMQListener<SysLog> {

    @Override
    public void onMessage(SysLog sysLog) {
        log.info("Receive message：" + sysLog);
        //如果消费失败，则抛出RuntimeException，RocketMQ会自动重试
        //可以手动抛出，也可以使用 Lombok 的 @SneakyThrows 注解来抛出 RuntimeException
    }
}

