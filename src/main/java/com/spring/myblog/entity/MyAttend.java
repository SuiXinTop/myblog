package com.spring.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (MyAttend)实体类
 *
 * @author makejava
 * @since 2021-11-13 11:53:05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyAttend implements Serializable {
    private static final long serialVersionUID = -57465757444703768L;
    @TableId(type = IdType.AUTO)
    private Integer attendId;
    
    private Integer attendUserId;
    
    private Integer fansUserId;
    
    private Date attendTime;
    
    private Integer attendState;

}