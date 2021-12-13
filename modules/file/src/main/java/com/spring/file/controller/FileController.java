package com.spring.file.controller;

import com.spring.common.entity.dto.RestMsg;
import com.spring.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-19
 * @描述
 */
@Slf4j
@RestController
@RequestMapping("file")
@Api(tags = "文件上传模块")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @SneakyThrows(Exception.class)
    @PostMapping("/userImg")
    @ApiOperation(value = "用户头像上传")
    public RestMsg upLoadUserImg(@RequestParam(value = "file") MultipartFile file,
                                 @RequestParam(value = "userId") Integer userId) {
        return fileService.upLoadUserFile(file, userId);
    }

    @SneakyThrows(Exception.class)
    @PostMapping("/blogImg")
    @ApiOperation(value = "博客封面上传")
    public RestMsg upLoadBlogImg(@RequestParam(value = "file") MultipartFile file,
                                 @RequestParam(value = "blogId") Integer blogId) {
        return fileService.upLoadBlogImg(file, blogId);
    }

    @SneakyThrows(Exception.class)
    @PostMapping("/all")
    @ApiOperation(value = "文件上传",notes = "type:['chat','blog','blogImg']")
    public RestMsg upLoadBlog(@RequestParam(value = "file") MultipartFile file,
                              @RequestParam(value = "type") String type) {
        return fileService.upLoad(file,type);
    }
}
