package com.spring.myblog.common.util;

import org.springframework.util.DigestUtils;

/**
 * @author STAR
 * @create 2021-09-10
 */
public class SecurityUtil {
    public static String getMd5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }
}
