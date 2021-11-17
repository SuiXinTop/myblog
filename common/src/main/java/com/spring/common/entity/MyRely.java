package com.spring.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    
    private Integer replyOwner;

    @TableField(exist = false)
    private MyUser owner;
    
    private String replyBody;
    
    private Date replyTime;
    
    private Integer replyState;

}