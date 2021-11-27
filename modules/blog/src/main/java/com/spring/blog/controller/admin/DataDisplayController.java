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
@RequestMapping("admin/display")
@RequiredArgsConstructor
@Api(tags = "后台数据显示",consumes = "admin")
public class DataDisplayController {
    private final RedisService redisService;

    @GetMapping("/online")
    public RestMsg getOnlineCount() {
        return RestMsg.success("");
    }

    @GetMapping("/blogCount")
    public RestMsg blogCount() {
        return RestMsg.success("");
    }

    @GetMapping("/userCount")
    public RestMsg userCount() {
        return RestMsg.success("");
    }

}
