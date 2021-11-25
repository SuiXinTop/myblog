package com.spring.common.util;

import cn.hutool.core.date.DateTime;
import com.spring.common.entity.dto.UserLogin;
import com.spring.common.entity.dto.UserRegister;
import com.spring.common.entity.dto.UserSecurity;
import com.spring.common.entity.po.User;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-25
 * @描述
 */
public class UserUtil {
    public static User createLoginUser(UserLogin r) {
        return User.builder()
                .userEmail(r.getUserEmail())
                .userPassword(SecurityUtil.getMd5Hex(r.getUserPassword()))
                .build();
    }

    public static User createRegisterUser(UserRegister r) {
        return User.builder()
                .userName(r.getUserName())
                .userEmail(r.getUserEmail())
                .userPassword(SecurityUtil.getMd5Hex(r.getUserPassword()))
                .registerTime(new DateTime())
                .build();
    }

    public static User createSecurityEmail(UserSecurity s) {
        return User.builder()
                .userId(s.getUserId())
                .userEmail(s.getUserEmail())
                .build();
    }

    public static User createSecurityPassword(UserSecurity s) {
        return User.builder()
                .userId(s.getUserId())
                .userPassword(s.getUserPassword())
                .build();
    }

}
