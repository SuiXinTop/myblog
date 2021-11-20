package com.spring.common.entity;

import com.baomidou.mybatisplus.annotation.*;
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

    @TableField(exist = false)
    private MyUser attendUser;
    
    private Integer fansUserId;

    @TableField(exist = false)
    private MyUser fansUser;

    private Date attendTime;

    @TableLogic(value = "1", delval = "0")
    private Integer attendState;

}