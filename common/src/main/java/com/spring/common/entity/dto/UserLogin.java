package com.spring.common.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-23
 * @描述
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin implements Serializable {

    private static final long serialVersionUID = -86422308011787984L;

    private String userEmail;

    private String userPassword;
}
