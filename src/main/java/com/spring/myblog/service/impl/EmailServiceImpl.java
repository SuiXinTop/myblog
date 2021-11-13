package com.spring.myblog.service.impl;

import com.spring.myblog.common.constant.RedisConstant;
import com.spring.myblog.common.exception.user.EmailCodeNotExitException;
import com.spring.myblog.common.util.RedisService;
import com.spring.myblog.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @author xxx
 * @create 2021-10-10
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final RedisService redisService;
    private final JavaMailSenderImpl mailSender;

    @Override
    public void sendHtmlMail(String path, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mineHelper = new MimeMessageHelper(message, true);
            //谁发
            mineHelper.setFrom("1270857044@qq.com");
            //谁要接收
            mineHelper.setTo(path);
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
                    "<h1>" + code + "</h1>\n" +
                    "<br>请复制验证码，前往注册页面 </br>\n" +
                    "<br><a href=\"http://118.31.15.127\">前往首页</a></br>\n" +
                    "<br>请注意：如果您无法访问此链接，请复制完整的 URL 并将其粘贴到您的浏览器中。 </br>\n" +
                    "<br>Blog团队</br>\n" +
                    "</body>\n" +
                    "</html>";
            mineHelper.setText(content, true);
            mailSender.send(message);
            redisService.setExpire(RedisConstant.EMAIL_PREFIX + path, code, RedisConstant.EMAIL_EXPIRE_TIME);
        } catch (Exception e) {
            throw new EmailCodeNotExitException();
        }
    }
}
