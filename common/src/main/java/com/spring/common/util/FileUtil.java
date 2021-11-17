package com.spring.common.util;


import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传工具类
 *
 * @author STARS
 */
public class FileUtil {
    private final static List<String> ALLOWED_EXTENSION =new ArrayList<>(){
        {
            add("jpg");
            add("png");
            add("jpeg");
            add("text");
        }
    };

    /**
     * 编码文件名
     */
    public static String extractFilename(MultipartFile file) {
        String name= file.getName();
        String extension = file.getOriginalFilename();
        if(StrUtil.isEmpty(extension)){
            extension="text";
        }
        if(!isAllowedExtension(extension)){
            return "未允许MIME类型";
        }
        return name + "-" + UUID.randomUUID() + "." + extension;
    }


    private static String getPathFileName(String fileName) {
        return "/" + fileName;
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension        上传文件类型
     * @return true/false
     */
    public static boolean isAllowedExtension(String extension) {
       return ALLOWED_EXTENSION.stream().anyMatch(s -> s.equals(extension));
    }

}