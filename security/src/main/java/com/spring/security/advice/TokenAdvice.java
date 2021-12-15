package com.spring.security.advice;

import com.spring.common.constant.RedisConstant;
import com.spring.common.exception.UnauthorizedException;
import com.spring.common.util.RequestUtil;
import com.spring.common.util.SecurityUtil;
import com.spring.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xxx
 * @create 2021-10-16
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TokenAdvice {
    private final HttpServletRequest request;
    private final RedisService redisService;

    /**
     * 定义一个切点
     */
    @Pointcut("@annotation(com.spring.security.annotation.PreAuth)")
    private void advicePointcut() {
    }

    /**
     * Around增强型,可以有返回值以及修改传参
     *
     * @return the string
     */
    @Before(value = "advicePointcut()")
    public String logAdvice() {
        String token = request.getHeader("authorization");
        if (token == null) {
            throw new UnauthorizedException("未登录");
        }
        String ip = RequestUtil.getIpAddress(request);
        String key = RedisConstant.TOKEN_PREFIX + SecurityUtil.getMd5Key(token, ip);
        if (!redisService.hasKey(key)) {
            throw new UnauthorizedException("登录信息已过期");
        }
        if (redisService.selectExpire(key) <= RedisConstant.TOKEN_REFRESH_TIME) {
            redisService.expire(key, RedisConstant.REFRESH_EXPIRE_TIME);
        }
        return key;
    }
}
