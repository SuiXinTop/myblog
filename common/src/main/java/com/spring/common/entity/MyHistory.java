package com.spring.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (MyHistory)实体类
 *
 * @author makejava
 * @since 2021-11-18 14:50:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyHistory implements Serializable {

    private static final long serialVersionUID = 577892847350734239L;

    @TableId(type = IdType.AUTO)
    private Integer historyId;
    
    private Integer blogId;

    @TableField(exist = false)
    private MyBlog myBlog;
    
    private Integer userId;

    private Date historyTime;
}