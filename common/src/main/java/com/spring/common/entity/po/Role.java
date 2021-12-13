package com.spring.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * (MyRole)实体类
 *
 * @author makejava
 * @since 2021-11-13 11:54:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("my_role")
public class Role implements Serializable {

    private static final long serialVersionUID = -84322870608859239L;

    @TableId(type = IdType.AUTO)
    private Integer roleId;

    @NotBlank
    private String roleKey;

    @NotBlank
    private String roleName;

}