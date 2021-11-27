package com.spring.blog.controller.admin;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 暂时不开发
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-25
 * @描述
 */
@RestController
@RequestMapping("admin/reply")
@RequiredArgsConstructor
@Api(tags = "回复后台操作",consumes = "admin")
public class ReplyManage {
}
