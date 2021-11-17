package com.spring.rocket.consumer;

import com.spring.common.constant.RedisConstant;
import com.spring.common.entity.EmailCode;
import com.spring.rocket.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-13
 * @描述
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = "EmailCode",consumerGroup = "consumer-email")
public class EmailConsumer implements RocketMQListener<EmailCode>, RocketMQPushConsumerLifecycleListener {
    private final RedisService redisService;
    private final JavaMailSenderImpl mailSender;

    @Override
    public void onMessage(EmailCode emailCode) {
        log.info("收到发送邮件消息");
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mineHelper = new MimeMessageHelper(message, true);
            //谁发
            mineHelper.setFrom("1270857044@qq.com");
            //谁要接收
            mineHelper.setTo(emailCode.getEmail());
            //邮件主题
            mineHelper.setSubject("注册验证码");
            //邮件内容   true 表示带有附件或html
            String content = "<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    "body{\n" +
                    "text-align:center;\n" +
                    "}\n" +
                    "p {\n" +
                    "font-size: 20pt;\n" +
                    "}\n" +
                    "h1{\n" +
                    "color: white;\n" +
                    "background-color: rgb(27, 221, 255);\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<br>尊敬的客户!您好 </br>\n" +
                    "<p>您最近注册了微博，请复制验证码</p>\n" +
                    "<h1>" + emailCode.getCode() + "</h1>\n" +
                    "<br>请复制验证码，前往注册页面 </br>\n" +
                    "<br><a href=\"http://118.31.15.127\">前往首页</a></br>\n" +
                    "<br>请注意：如果您无法访问此链接，请复制完整的 URL 并将其粘贴到您的浏览器中。 </br>\n" +
                    "<br>Blog团队</br>\n" +
                    "</body>\n" +
                    "</html>";
            mineHelper.setText(content, true);
            mailSender.send(message);
            redisService.setExpire(RedisConstant.EMAIL_PREFIX + emailCode.getEmail(),
                    emailCode.getCode(), RedisConstant.EMAIL_EXPIRE_TIME);
            log.info("已向{}发送邮件:",emailCode.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("邮件发送失败");
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // 每次拉取的间隔，单位为毫秒
        consumer.setPullInterval(5000);
        consumer.setConsumeThreadMin(2);
        consumer.setConsumeThreadMax(4);
        // 设置每次从队列中拉取的消息数为16
        consumer.setPullBatchSize(10);
        consumer.setConsumeMessageBatchMaxSize(5);
    }
}
