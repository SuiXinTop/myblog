package com.spring.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-24
 * @描述
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.spring")
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}
