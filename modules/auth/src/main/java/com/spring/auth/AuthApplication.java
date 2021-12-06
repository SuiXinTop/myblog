package com.spring.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-27
 * @描述
 */
@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = "com.spring")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
