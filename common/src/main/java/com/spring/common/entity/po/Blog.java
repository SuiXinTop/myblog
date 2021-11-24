package com.spring.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (MyBlog)实体类
 *
 * @author makejava
 * @since 2021-11-13 11:53:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("my_blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = -60278110476686887L;

    @TableId(type = IdType.AUTO)
    private Integer blogId;

    private String blogTitle;

    private String blogBody;

    private String blogImg;

    private Date blogTime;

    private Date blogUpdateTime;

    private Integer blogCollect;

    private Integer blogView;

    private Integer blogLike;

    private Integer blogComment;

    @TableLogic(value = "1", delval = "0")
    private Integer blogState;

    private Integer userId;

}