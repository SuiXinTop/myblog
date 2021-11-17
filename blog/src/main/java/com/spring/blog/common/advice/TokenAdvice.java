package com.spring.blog.common.advice;

import com.spring.blog.redis.RedisService;
import com.spring.common.constant.RedisConstant;
import com.spring.common.entity.MyUser;
import com.spring.common.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
    @Pointcut("@annotation(com.spring.blog.common.annotation.PreAuth)")
    private void advicePointcut() {
    }

    /**
     * Around增强型,可以有返回值以及修改传参
     *
     * @param joinPoint the join point
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("advicePointcut()")
    public Object logAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        String token = request.getHeader("authorization");
        if (token == null) {
            throw new RuntimeException("未通过授权");
        }
        if (!redisService.hasKey(token)) {
            MyUser user = TokenUtil.verifyToken(token);
            if (user == null) {
                throw new RuntimeException("登录信息已过期，请重新登录");
            }
            redisService.setExpire(RedisConstant.TOKEN_PREFIX + token, user, RedisConstant.TOKEN_EXPIRE_TIME);
        }
        return joinPoint.proceed();
    }
}
