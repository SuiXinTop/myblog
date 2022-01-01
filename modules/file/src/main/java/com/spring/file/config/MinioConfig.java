package com.spring.file.config;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Minio 配置信息
 *
 * @author STARS
 */
@Configuration
public class MinioConfig {
    /**
     * 服务地址
     */
    public static final String URL = "http://118.31.15.127:9000";

    /**
     * 用户名
     */
    public static final String ACCESS_KEY = "suixintop";

    /**
     * 密码
     */
    public static final String SECRET_KEY = "suixintop";

    /**
     * 存储桶名称
     */
    public static final String BUCKET_NAME = "blog";

    /**
     * 存储前缀
     */
    public static final String FILE_PREFIX = "http://118.31.15.127:9000/blog";

    public static PutObjectArgs createArgs(MultipartFile file, String fileName) throws IOException {
        return PutObjectArgs.builder()
                .bucket(BUCKET_NAME)
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
    }


    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(URL)
                .credentials(ACCESS_KEY, SECRET_KEY)
                .build();
    }
}
