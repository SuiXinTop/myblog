package com.spring.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (MyTag)实体类
 *
 * @author makejava
 * @since 2021-11-13 11:54:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyTag implements Serializable {
    private static final long serialVersionUID = -59456370788776577L;
    @TableId(type = IdType.AUTO)
    private Integer tagId;
    
    private String tagName;

}