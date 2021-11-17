package com.spring.common.constant;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-13
 * @描述
 */
public class RedisConstant {

    public final static String TOKEN_PREFIX = "token:";

    public final static long TOKEN_EXPIRE_TIME = 60L;

    public final static String EMAIL_PREFIX = "email:";

    public final static long EMAIL_EXPIRE_TIME = 5L;

    public final static String LOGIN_TIMES_PREFIX = "loginTimes:";

    public final static long LOGIN_EXPIRE_TIME = 30L;

    public final static int LOGIN_LIMIT = 5;
}
