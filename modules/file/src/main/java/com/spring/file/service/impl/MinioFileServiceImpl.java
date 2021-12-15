package com.spring.file.service.impl;

import cn.hutool.core.date.DateTime;
import com.spring.common.constant.FileConstant;
import com.spring.common.constant.HttpConstant;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.po.Blog;
import com.spring.common.entity.po.User;
import com.spring.common.exception.ServiceException;
import com.spring.common.util.FileOperUtil;
import com.spring.file.config.MinioConfig;
import com.spring.file.service.FileService;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * Minio 文件存储
 *
 * @author ruoyi
 */
@Service("minioService")
@RequiredArgsConstructor
public class MinioFileServiceImpl implements FileService {
    private final MinioClient minioClient;
    private final RestTemplate restTemplate;

    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg upLoadUserFile(MultipartFile file, Integer userId) throws Exception {
        String fileName = FileConstant.USER_HEAD_PATH + FileOperUtil.extractFilename(file);

        minioClient.putObject(MinioConfig.createArgs(file, fileName));

        String path = MinioConfig.FILE_PREFIX + fileName;
        User user = User.builder().userId(userId).userImg(path).build();
        HttpEntity<User> httpEntity = new HttpEntity<>(user);
        ResponseEntity<RestMsg> responseEntity = this.restTemplate.exchange(
                "http://localhost:8080/user/info",
                HttpMethod.PUT, httpEntity, RestMsg.class);
        RestMsg restMsg = responseEntity.getBody();

        assert restMsg != null;
        if (restMsg.getCode() != HttpConstant.SUCCESS) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }

        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, path);
    }

    @Override
    public RestMsg upLoadBlogImg(MultipartFile file, Integer blogId) throws Exception {
        String fileName = FileConstant.BLOG_IMG_PATH + FileOperUtil.extractFilename(file);

        minioClient.putObject(MinioConfig.createArgs(file, fileName));

        String path = MinioConfig.FILE_PREFIX + fileName;
        Blog blog = Blog.builder().blogId(blogId).blogImg(path).blogUpdateTime(new DateTime()).build();
        HttpEntity<Blog> httpEntity = new HttpEntity<>(blog);
        ResponseEntity<RestMsg> responseEntity = this.restTemplate.exchange(
                "http://localhost:8080/blog",
                HttpMethod.PUT, httpEntity, RestMsg.class);
        RestMsg restMsg = responseEntity.getBody();

        assert restMsg != null;
        if (restMsg.getCode() != HttpConstant.SUCCESS) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }

        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, path);
    }

    @Override
    public RestMsg upLoad(MultipartFile file, String type) throws Exception {
        if (file.isEmpty()) {
            throw new ServiceException(MsgConstant.UPLOAD_FAULT);
        }

        String fileName;
        switch (type) {
            case "blog":
                fileName = FileConstant.BLOG_CONTENT_PATH + FileOperUtil.extractFilename(file);
                break;
            case "blogImg":
                fileName = FileConstant.BLOG_IMG_PATH + FileOperUtil.extractFilename(file);
                break;
            case "chat":
                fileName = FileConstant.CHAT_CONTENT_PATH + FileOperUtil.extractFilename(file);
                break;
            default:
                throw new ServiceException(MsgConstant.UPLOAD_FAULT);
        }

        minioClient.putObject(MinioConfig.createArgs(file, fileName));

        String path = MinioConfig.FILE_PREFIX + fileName;
        return RestMsg.success(MsgConstant.UPLOAD_SUCCESS, path);
    }

}
