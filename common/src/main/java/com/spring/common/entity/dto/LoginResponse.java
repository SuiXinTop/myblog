package com.spring.common.entity.dto;

import com.spring.common.entity.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-12-06
 * @描述
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {
    String token;
    UserVo userVo;
}
