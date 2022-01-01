package com.spring.common.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import java.io.Serializable;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-13
 * @描述
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailCode implements Serializable {

    @Email
    private String email;

    @Length(min=4, max=4)
    private String code;
}
