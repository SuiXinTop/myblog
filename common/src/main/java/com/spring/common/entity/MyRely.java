package com.spring.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (MyRely)实体类
 *
 * @author makejava
 * @since 2021-11-13 11:54:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyRely implements Serializable {

    private static final long serialVersionUID = 790705687502029413L;

    @TableId(type = IdType.AUTO)
    private Integer replyId;
    
    private Integer comId;

    @TableField(exist = false)
    private MyComment myComment;
    
    private Integer replyOwner;

    @TableField(exist = false)
    private MyUser owner;
    
    private String replyBody;

    private Date replyTime;

    @TableLogic(value = "1", delval = "0")
    private Integer replyState;

}