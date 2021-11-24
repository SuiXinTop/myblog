package com.spring.common.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
@TableName("my_attend")
public class Attend implements Serializable {

    private static final long serialVersionUID = -57465757444703768L;

    @TableId(type = IdType.AUTO)
    private Integer attendId;
    
    private Integer attendUserId;

    private Integer fansUserId;

    private Date attendTime;

    @TableLogic(value = "1", delval = "0")
    private Integer attendState;

}