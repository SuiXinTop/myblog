package com.spring.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (MyBlogTag)实体类
 *
 * @author makejava
 * @since 2021-11-13 11:53:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyBlogTag implements Serializable {

    private static final long serialVersionUID = -27003107228331626L;

    @TableId(type = IdType.AUTO)
    private Integer blogTagId;
    
    private Integer blogId;
    
    private Integer tagId;

    @TableField(exist = false)
    private MyTag myTag;
}