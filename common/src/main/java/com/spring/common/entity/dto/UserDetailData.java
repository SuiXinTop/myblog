package com.spring.common.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 传递用户博客数据的类
 *
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-12-18
 * @描述
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailData implements Serializable {
    private int blogCount = 0;
    private int collectCount = 0;
    private int attendCount = 0;
    private int fansCount = 0;
}
