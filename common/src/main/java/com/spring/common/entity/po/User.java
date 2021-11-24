package com.spring.common.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (MyUser)实体类
 *
 * @author makejava
 * @since 2021-11-13 11:54:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("my_user")
public class User implements Serializable {

    private static final long serialVersionUID = -86422308011787984L;

    @TableId(type = IdType.AUTO)
    private Integer userId;

    private String userName;

    private String userPassword;

    private String userImg;

    private String userEmail;

    private String userAddress;

    private String userSex;

    @TableLogic(value = "1", delval = "0")
    private Integer userState;

    private String loginIp;

    private Date loginTime;

    private Date registerTime;

    private Integer roleId;

}