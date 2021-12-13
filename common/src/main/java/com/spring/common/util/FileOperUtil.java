package com.spring.common.util;


import cn.hutool.core.io.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * 文件上传工具类
 *
 * @author STARS
 */
public class FileOperUtil {

    private final static List<String> IMG_EXTENSION = List.of(new String[]{"jpg", "bmp", "png", "jpeg", "gif"});

    /**
     * 编码文件名
     */
    public static String extractFilename(MultipartFile file) {
        String extension = FileUtil.getSuffix(file.getOriginalFilename());
//        if (!isAllowedExtension(extension)) {
//            throw new ServiceException("未允许MIME类型");
//        }
        String name = SecurityUtil.getMd5Hex(file.getOriginalFilename());
        return name + "-" + UUID.randomUUID() + "." + extension;
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension 上传文件类型
     * @return true/false
     */
    private static boolean isAllowedExtension(String extension) {
        return IMG_EXTENSION.stream().anyMatch(s -> s.equals(extension));
    }

}