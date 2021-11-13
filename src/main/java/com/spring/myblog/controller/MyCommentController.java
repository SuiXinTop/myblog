package com.spring.myblog.controller;

import com.spring.myblog.service.MyCommentService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MyComment)表控制层
 *
 * @author makejava
 * @since 2021-11-13 11:53:51
 */
@RestController
@RequestMapping("myComment")
@Api(tags = "评论管理模块")
public class MyCommentController {


}