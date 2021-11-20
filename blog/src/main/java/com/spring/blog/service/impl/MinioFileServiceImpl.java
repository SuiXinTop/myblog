package com.spring.blog.service.impl;

import com.spring.blog.config.MinioConfig;
import com.spring.blog.dao.MyBlogDao;
import com.spring.blog.dao.MyUserDao;
import com.spring.blog.service.FileService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.MyBlog;
import com.spring.common.entity.MyUser;
import com.spring.common.exception.ServiceException;
import com.spring.common.model.RestMsg;
import com.spring.common.util.FileOperUtil;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Minio 文件存储
 *
 * @author ruoyi
 */
@Service
@RequiredArgsConstructor
public class MinioFileServiceImpl implements FileService {
    private final MinioClient minioClient;
    private final MyUserDao myUserDao;
    private final MyBlogDao myBlogDao;

    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     */
    @Override
    public RestMsg upLoadUserFile(MultipartFile file, Integer userId) throws Exception {
        String fileName = "/userImg/" + FileOperUtil.extractFilename(file);

        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(MinioConfig.BUCKET_NAME)
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);
        String url = MinioConfig.FILE_PREFIX + fileName;
        MyUser myUser = MyUser.builder().userId(1).userImg(url).build();
        if (myUserDao.updateById(myUser) == 0) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, url);
    }

    @Override
    public RestMsg upLoadBlogImg(MultipartFile file, Integer blogId) throws Exception {
        String fileName = "/blogImg/" + FileOperUtil.extractFilename(file);

        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(MinioConfig.BUCKET_NAME)
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);

        String url = MinioConfig.FILE_PREFIX + fileName;
        MyBlog myBlog = MyBlog.builder().blogId(1).blogImg(url).build();
        if (myBlogDao.updateById(myBlog) == 0) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, url);
    }

    @Override
    public RestMsg upLoadBlogContent(MultipartFile[] files) throws Exception{
        int length = files.length;
        List<String> list = new ArrayList<>();
        if(length==0){
            throw new ServiceException(MsgConstant.UPLOAD_FAULT);
        }
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new ServiceException(MsgConstant.UPLOAD_FAULT);
            }
            String fileName = "/blogContent/" + FileOperUtil.extractFilename(file);
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
        return RestMsg.success(MsgConstant.UPLOAD_SUCCESS,list);
    }


}
