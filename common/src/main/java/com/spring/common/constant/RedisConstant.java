package com.spring.common.constant;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-13
 * @描述
 */
public class RedisConstant {

    public final static String TOKEN_PREFIX = "token:";

    /**
     * 刷新的时间点，单位：s
     */
    public final static long TOKEN_REFRESH_TIME = 3600L;

    public final static long REFRESH_EXPIRE_TIME = 60L;

    public final static long TOKEN_EXPIRE_TIME = 24*60L;

    public final static String EMAIL_PREFIX = "email:";

    public final static long EMAIL_EXPIRE_TIME = 5L;

    public final static String LOGIN_TIMES_PREFIX = "login:times:";

    public final static long LOGIN_EXPIRE_TIME = 30L;

    public final static int LOGIN_LIMIT = 5;

    public final static String BLOG_PREFIX = "blog:select:";

    public final static long BLOG_EXPIRE_TIME = 5L;

    public final static String BLOG_HOT= "blog:hot";

    public final static long HOT_EXPIRE_TIME = 10L;

    public final static String BLOG_TEMP= "blog:temp:";
}
