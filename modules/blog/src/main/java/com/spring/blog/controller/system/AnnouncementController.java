package com.spring.blog.controller.system;

import com.spring.security.annotation.PreRole;
import com.spring.blog.service.AnnouncementService;
import com.spring.common.constant.RoleConstant;
import com.spring.common.entity.po.Announcement;
import com.spring.common.entity.dto.RestMsg;
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
@RequestMapping("announcement")
@Api(tags = "系统公告模块",consumes = "admin")
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @PostMapping("")
    @ApiOperation(value = "新建系统公告")
    @PreRole(role = RoleConstant.SUPER_ADMIN)
    public RestMsg insert(@RequestBody Announcement announcement) {
        return announcementService.insert(announcement);
    }

    @PutMapping("")
    @ApiOperation(value = "修改系统公告")
    @PreRole(role = RoleConstant.SUPER_ADMIN)
    public RestMsg update(@RequestBody Announcement announcement) {
        return announcementService.update(announcement);
    }

    @DeleteMapping("")
    @ApiOperation(value = "删除系统公告")
    @PreRole(role = RoleConstant.SUPER_ADMIN)
    public RestMsg delete(@RequestBody List<Integer> amtIdList) {
        return announcementService.delete(amtIdList);
    }

    @PostMapping("/top")
    @ApiOperation(value = "置顶系统公告")
    @PreRole(role = RoleConstant.SUPER_ADMIN)
    public RestMsg top(@RequestBody List<Integer> amtIdList) {
        return announcementService.top(amtIdList);
    }


    @GetMapping("/top")
    @ApiOperation(value = "获取置顶公告")
    public RestMsg selectTop() {
        return RestMsg.success("");
    }

    @GetMapping("")
    @ApiOperation(value = "查看非置顶系统公告")
    public RestMsg select(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                          @RequestParam(value = "isAsc", defaultValue = "0") int isAsc) {
        return announcementService.select(pageNum,pageSize,isAsc);
    }

}