package com.spring.file.controller;

import com.spring.common.entity.dto.RestMsg;
import com.spring.file.service.FileService;
import com.spring.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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
public class FileController {
    @Resource(name = "minioService")
    private FileService fileService;

    @SneakyThrows(Exception.class)
    @PreAuth
    @PostMapping("/userImg")
    @ApiOperation(value = "用户头像上传")
    public RestMsg upLoadUserImg(@RequestParam(value = "file") MultipartFile file,
                                 @RequestParam(value = "userId") Integer userId) {
        return fileService.upLoadUserFile(file, userId);
    }

    @SneakyThrows(Exception.class)
    @PreAuth
    @PostMapping("/blogImg")
    @ApiOperation(value = "博客封面上传")
    public RestMsg upLoadBlogImg(@RequestParam(value = "file") MultipartFile file,
                                 @RequestParam(value = "blogId") Integer blogId) {
        return fileService.upLoadBlogImg(file, blogId);
    }

    @SneakyThrows(Exception.class)
    @PreAuth
    @PostMapping("/all")
    @ApiOperation(value = "文件上传", notes = "type:['chat','blog','blogImg']")
    public RestMsg upLoadBlog(@RequestParam(value = "file") MultipartFile file,
                              @RequestParam(value = "type") String type) {
        return fileService.upLoad(file, type);
    }

}
