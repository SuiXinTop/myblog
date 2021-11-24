package com.spring.common.util;

import static cn.hutool.crypto.digest.DigestUtil.md5Hex;

/**
 * @author STAR
 * @create 2021-09-10
 */
public class SecurityUtil {
    private final static String SALT ="suixin";

    public static String getMd5Key(String token,String ip){
        return  md5Hex(token+ip);
    }

    public static String getMd5Hex(String password){
        return md5Hex(password+SALT);
    }

    public static boolean verifyPassword(String password, String md5) {
        return getMd5Hex(password).equals(md5);
    }
}
