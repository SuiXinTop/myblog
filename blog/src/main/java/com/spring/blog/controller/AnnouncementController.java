package com.spring.blog.controller;

import com.spring.blog.common.annotation.PreRole;
import com.spring.blog.service.AnnouncementService;
import com.spring.common.constant.RoleConstant;
import com.spring.common.entity.Announcement;
import com.spring.common.model.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (MyAnnouncement)表控制层
 *
 * @author makejava
 * @since 2021-11-13 11:52:24
 */
@RestController
@RequestMapping("myAnnouncement")
@Api(tags = "系统公告模块")
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @PostMapping("/insert")
    @ApiOperation(value = "新建系统公告")
    @PreRole(role = RoleConstant.SUPER_ADMIN)
    public RestMsg insert(@RequestBody Announcement announcement) {
        return announcementService.insert(announcement);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改系统公告")
    @PreRole(role = RoleConstant.SUPER_ADMIN)
    public RestMsg update(@RequestBody Announcement announcement) {
        return announcementService.update(announcement);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除系统公告")
    @PreRole(role = RoleConstant.SUPER_ADMIN)
    public RestMsg delete(@RequestBody List<Integer> amtIds) {
        return announcementService.delete(amtIds);
    }

    @PostMapping("/top")
    @ApiOperation(value = "置顶系统公告")
    @PreRole(role = RoleConstant.SUPER_ADMIN)
    public RestMsg top(@RequestBody List<Integer> amtIds) {
        return announcementService.top(amtIds);
    }

    @GetMapping("/select")
    @ApiOperation(value = "查看系统公告，置顶倒序")
    public RestMsg select(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                          @RequestParam(value = "isAsc", defaultValue = "0") int isAsc) {
        return announcementService.select(pageNum,pageSize,isAsc);
    }

}