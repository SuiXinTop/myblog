package com.spring.blog.controller;

import com.spring.blog.service.FileService;
import com.spring.common.exception.ServiceException;
import com.spring.common.model.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/upLoadUserImg")
    @ApiOperation(value = "用户头像上传")
    public RestMsg upLoadUserImg(@RequestParam(value = "file") MultipartFile file,
                                 @RequestParam(value = "userId") Integer userId) {
        try {
            return fileService.upLoadUserFile(file, userId);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @PostMapping("/upLoadBlogImg")
    @ApiOperation(value = "博客封面上传")
    public RestMsg upLoadBlogImg(@RequestParam(value = "file") MultipartFile file,
                                 @RequestParam(value = "blogId") Integer blogId) {
        try {
            return fileService.upLoadBlogImg(file, blogId);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @PostMapping("/upLoadBlog")
    @ApiOperation(value = "博客内容上传")
    public RestMsg upLoadBlog(@RequestParam(value = "file") MultipartFile[] files) {
        try {
            return fileService.upLoadBlogContent(files);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
