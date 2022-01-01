package com.spring.common.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-25
 * @描述
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserSecurity extends UserLogin implements Serializable {
    private static final long serialVersionUID = -86422308011787984L;

    @NotBlank
    private Integer userId;

    @Length(min=4, max=4)
    private String code;

}
