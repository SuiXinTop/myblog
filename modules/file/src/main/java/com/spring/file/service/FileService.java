package com.spring.file.service;

import com.spring.common.entity.dto.RestMsg;
import org.springframework.web.multipart.MultipartFile;

/**
 * The interface File service.
 *
 * @author xxx
 * @create 2021 -11-04
 */
public interface FileService {
    /**
     * Upload file string.
     *
     * @param file   the file
     * @param userId the user id
     * @return the string
     * @throws Exception the exception
     */
    RestMsg upLoadUserFile(MultipartFile file,Integer userId) throws Exception;

    /**
     * Up load blog img rest msg.
     *
     * @param file   the file
     * @param blogId the blog id
     * @return the rest msg
     * @throws Exception the exception
     */
    RestMsg upLoadBlogImg(MultipartFile file,Integer blogId) throws Exception;

    /**
     * Up load blog content rest msg.
     *
     * @param files the files
     * @return the rest msg
     * @throws Exception the exception
     */
    RestMsg upLoadBlogContent(MultipartFile[] files) throws Exception;

}
