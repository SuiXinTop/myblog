package com.spring.myblog.controller;

import com.spring.myblog.service.MyAnnouncementService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MyAnnouncement)表控制层
 *
 * @author makejava
 * @since 2021-11-13 11:52:24
 */
@RestController
@RequestMapping("myAnnouncement")
@Api(tags = "系统公告模块")
public class AnnouncementController {

}