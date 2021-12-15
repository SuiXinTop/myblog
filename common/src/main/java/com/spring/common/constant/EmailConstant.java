package com.spring.common.constant;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-19
 * @描述
 */
public class EmailConstant {
    public final static String FROM = "1270857044@qq.com";
    public final static String SUBJECT_REGISTER = "注册验证码";
    public final static String REGISTER_CONTENT_PREFIX =
            "<html>\n" +
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
            "<h1>";
    public final static String REGISTER_CONTENT_SUFFIX =
            "</h1>\n" +
            "<br>请复制验证码，前往注册页面 </br>\n" +
            "<br><a href=\"http://118.31.15.127\">前往首页</a></br>\n" +
            "<br>请注意：如果您无法访问此链接，请复制完整的 URL 并将其粘贴到您的浏览器中。 </br>\n" +
            "<br>Blog团队</br>\n" +
            "</body>\n" +
            "</html>";

    public final static String SUBJECT_VERIFY = "操作验证码";
    public final static String VERIFY_CONTENT_PREFIX =
            "<html>\n" +
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
                    "<h1>";
    public final static String VERIFY_CONTENT_SUFFIX =
            "</h1>\n" +
                    "<br>请复制验证码，前往注册页面 </br>\n" +
                    "<br><a href=\"http://118.31.15.127\">前往首页</a></br>\n" +
                    "<br>请注意：如果您无法访问此链接，请复制完整的 URL 并将其粘贴到您的浏览器中。 </br>\n" +
                    "<br>Blog团队</br>\n" +
                    "</body>\n" +
                    "</html>";
}
