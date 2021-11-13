package com.spring.myblog;

import com.spring.myblog.dao.MyUserDao;
import com.spring.myblog.entity.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MyblogApplicationTests {
    @Autowired
    private MyUserDao myUserDao;

    @Test
    void contextLoads() {
    }
    @Test
    void name(){
        MyUser myUser=new MyUser();
        log.info(myUserDao.selectUserRole(myUser).toString());
    }
}
