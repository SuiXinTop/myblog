package com.spring.common.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-12-25
 * @描述
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemData implements Serializable {
    private int userCount = 0;

    private int blogCount = 0;

    private int announceCount = 0;
}
