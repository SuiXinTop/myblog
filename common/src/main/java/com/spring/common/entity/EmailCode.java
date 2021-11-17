package com.spring.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-13
 * @描述
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailCode {
    private String email;
    private String code;
}
