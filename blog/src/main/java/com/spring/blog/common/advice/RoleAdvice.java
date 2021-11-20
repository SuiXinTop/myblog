package com.spring.blog.common.advice;

import com.spring.blog.common.annotation.PreRole;
import com.spring.blog.redis.RedisService;
import com.spring.common.constant.RedisConstant;
import com.spring.common.entity.MyUser;
import com.spring.common.exception.user.RoleBlockedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-18
 * @描述
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RoleAdvice {
    private final RedisService redisService;
    private final TokenAdvice tokenAdvice;
    /**
     * Around增强型,可以有返回值以及修改传参
     *
     * @param preRole the pre role
     */
    @Before(value = "@annotation(preRole)")
    public void roleAdvice(PreRole preRole) {
        String token = tokenAdvice.logAdvice();
        log.info(token);
        MyUser myUser = (MyUser) redisService.get(RedisConstant.TOKEN_PREFIX + token);
        log.info(myUser.toString());
        String roleKey = myUser.getMyRole().getRoleKey();
        List<String> roles = Arrays.asList(preRole.role());
        if (roles.stream().noneMatch(s -> s.equals(roleKey))) {
            throw new RoleBlockedException();
        }
    }
}
