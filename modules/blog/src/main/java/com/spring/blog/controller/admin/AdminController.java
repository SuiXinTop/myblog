package com.spring.blog.controller.admin;

import com.spring.common.entity.dto.RestMsg;
import com.spring.redis.service.RedisService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-19
 * @描述
 */
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
@Api(tags = "后台操作(TODO)")
public class AdminController {
    private final RedisService redisService;

    @GetMapping("/expre")
    public RestMsg expre(){

        return RestMsg.success(redisService.del("2","3"));
    }
}
