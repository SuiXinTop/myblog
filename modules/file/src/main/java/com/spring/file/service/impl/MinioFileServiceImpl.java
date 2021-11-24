package com.spring.file.service.impl;

import com.spring.common.constant.FileConstant;
import com.spring.common.constant.MsgConstant;
import com.spring.common.enmu.Status;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.po.Blog;
import com.spring.common.entity.po.User;
import com.spring.common.exception.ServiceException;
import com.spring.common.util.FileOperUtil;
import com.spring.file.config.MinioConfig;
import com.spring.file.dao.BlogDao;
import com.spring.file.dao.UserDao;
import com.spring.file.service.FileService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Minio 文件存储
 *
 * @author ruoyi
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MinioFileServiceImpl implements FileService {
    private final MinioClient minioClient;
    private final UserDao userDao;
    private final BlogDao blogDao;

    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     */
    @Override
    public RestMsg upLoadUserFile(MultipartFile file, Integer userId) throws Exception {
        String fileName = FileConstant.USER_HEAD_PATH + FileOperUtil.extractFilename(file);

        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(MinioConfig.BUCKET_NAME)
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);
        String url = MinioConfig.FILE_PREFIX + fileName;
        User user = User.builder().userId(1).userImg(url).build();
        if (userDao.updateById(user) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, url);
    }

    @Override
    public RestMsg upLoadBlogImg(MultipartFile file, Integer blogId) throws Exception {
        String fileName = FileConstant.BLOG_IMG_PATH + FileOperUtil.extractFilename(file);

        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(MinioConfig.BUCKET_NAME)
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);

        String url = MinioConfig.FILE_PREFIX + fileName;
        Blog blog = Blog.builder().blogId(1).blogImg(url).build();
        if (blogDao.updateById(blog) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, url);
    }

    @Override
    public RestMsg upLoadBlogContent(MultipartFile[] files) throws Exception {
        int length = files.length;
        List<String> list = new ArrayList<>();
        if (length == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.UPLOAD_FAULT);
        }
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new ServiceException(MsgConstant.UPLOAD_FAULT);
            }
            String fileName = FileConstant.BLOG_CONTENT_PATH + FileOperUtil.extractFilename(file);
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(MinioConfig.BUCKET_NAME)
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            minioClient.putObject(args);
            String url = MinioConfig.FILE_PREFIX + fileName;
            list.add(url);
        }
        return RestMsg.success(MsgConstant.UPLOAD_SUCCESS, list);
    }


}
