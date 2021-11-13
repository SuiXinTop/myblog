package com.spring.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class MyRole implements Serializable {
    private static final long serialVersionUID = -84322870608859239L;
    @TableId(type = IdType.AUTO)
    private Integer roleId;
    
    private String roleKey;
    
    private String roleName;

}