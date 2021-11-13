package com.spring.myblog.common.constant;

import com.auth0.jwt.algorithms.Algorithm;

import java.util.HashMap;
import java.util.Map;


/**
 * @author STARS
 */
public class JwtConstant {

    public static final Algorithm KEY = Algorithm.HMAC256("and0X3ZhbGlkYXRpb25fY29uZmlnX2tleQ==");

    public static final int EXPIRES = 60;

    public static final int HEADER_NUMBER = 2;

    public static final int CLAIM_NUMBER = 2;

    public static final String USER_ID = "user_id";

    public static final String USER_NAME = "user_name";

}