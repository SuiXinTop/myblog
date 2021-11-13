package com.spring.myblog.common.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.spring.myblog.common.constant.JwtConstant;
import com.spring.myblog.common.constant.RedisConstant;
import com.spring.myblog.common.model.RestMsg;
import com.spring.myblog.entity.MyUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xxx
 * @create 2021-10-13
 */
public class TokenUtil {

    public static Map<String, Object> returnToken(MyUser myUser) {
        String token = TokenUtil.createToken(myUser);
        return new HashMap<>(1) {
            {
                put("token", token);
            }
        };
    }

    public static String createToken(MyUser myUser) {
        return JWT.create()
                .withHeader(createHeader())
                .withPayload(createPayload(myUser))
                .withExpiresAt(createExpire())
                .sign(JwtConstant.KEY);
    }

    public static Map<String, Object> createHeader() {
        return new HashMap<>(JwtConstant.HEADER_NUMBER) {
            {
                put("alg", "HMAC256");
                put("typ", "JWT");
            }
        };
    }

    public static Map<String, Object> createPayload(MyUser myUser) {
        Map<String, Object> claims = new HashMap<>(JwtConstant.CLAIM_NUMBER);
        claims.put(JwtConstant.USER_ID, myUser.getUserId());
        claims.put(JwtConstant.USER_NAME, myUser.getUserName());
        return claims;
    }

    public static Date createExpire() {
        return DateUtil.offset(new DateTime(), DateField.HOUR, JwtConstant.EXPIRES);
    }

    public static MyUser verifyToken(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(JwtConstant.KEY).build();
            DecodedJWT decodedJwt = jwtVerifier.verify(token);
            return MyUser.builder()
                    .userId(decodedJwt.getClaim(JwtConstant.USER_ID).asInt())
                    .userName(decodedJwt.getClaim(JwtConstant.USER_NAME).asString())
                    .build();
        } catch (JWTDecodeException de) {
            throw new JWTDecodeException("密钥格式不正确");
        } catch (SignatureVerificationException de) {
            throw new SignatureVerificationException(JwtConstant.KEY);
        } catch (TokenExpiredException tee) {
            throw new TokenExpiredException("密钥已过期");
        } catch (InvalidClaimException ice) {
            throw new InvalidClaimException("非法密钥");
        } catch (JWTVerificationException jve) {
            throw new JWTVerificationException("密钥解析错误");
        }
    }

}
