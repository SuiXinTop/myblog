package com.spring.blog.common.advice;

import com.spring.blog.dao.MyUserDao;
import com.spring.blog.redis.RedisService;
import com.spring.common.constant.RedisConstant;
import com.spring.common.entity.MyUser;
import com.spring.common.exception.UnauthorizedException;
import com.spring.common.util.TokenUtil;
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
    private final MyUserDao myUserDao;


    /**
     * 定义一个切点
     */
    @Pointcut("@annotation(com.spring.blog.common.annotation.PreAuth)")
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
            throw new UnauthorizedException();
        }
        if (!redisService.hasKey(token)) {
            MyUser newUser = myUserDao.selectUserRole(TokenUtil.verifyToken(token));
            redisService.setExpire(RedisConstant.TOKEN_PREFIX + token, newUser, RedisConstant.TOKEN_EXPIRE_TIME);
        }
        return token;
    }
}
