package com.spring.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@Document(indexName = "myblog")
public class MyBlog implements Serializable {

    private static final long serialVersionUID = -60278110476686887L;

    @Id
    @TableId(type = IdType.AUTO)
    private Integer blogId;

    private String blogTitle;

    private String blogBody;

    private String blogImg;

    private Date blogTime;

    private Integer blogCollect;

    private Integer blogView;

    private Integer blogLike;

    private Integer blogComment;

    @TableLogic(value = "1", delval = "0")
    private Object blogState;

    private Integer userId;

    @TableField(exist = false)
    private MyUser myUser;

    @TableField(exist = false)
    private List<MyBlogTag> myBlogTagList;

}