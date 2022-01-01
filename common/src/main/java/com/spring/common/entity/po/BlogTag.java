package com.spring.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@NoArgsConstructor
@TableName("my_blog_tag")
public class BlogTag implements Serializable {

    private static final long serialVersionUID = -27003107228331626L;

    @TableId(type = IdType.AUTO)
    private Integer blogTagId;

    private Integer blogId;

    private Integer tagId;


}