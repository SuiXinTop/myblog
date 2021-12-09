package com.spring.common.util;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.spring.common.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传工具类
 *
 * @author STARS
 */
public class FileOperUtil {
    private final static List<String> ALLOWED_EXTENSION = new ArrayList<>() {
        {
            add("jpg");
            add("png");
            add("jpeg");
            add("bmp");
            add("doc");
        }
    };

    /**
     * 编码文件名
     */
    public static String extractFilename(MultipartFile file) {
        String name = SecurityUtil.getMd5Hex(file.getOriginalFilename());
        String extension = FileUtil.getSuffix(file.getOriginalFilename());
        if (StrUtil.isEmpty(extension)) {
            extension = "txt";
        }
        System.out.println(extension);
        if (!isAllowedExtension(extension)) {
            throw new ServiceException("未允许MIME类型");
        }
        return name + "-" + UUID.randomUUID() + "." + extension;
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension 上传文件类型
     * @return true/false
     */
    private static boolean isAllowedExtension(String extension) {
        return ALLOWED_EXTENSION.stream().anyMatch(s -> s.equals(extension));
    }

}