package com.spring.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * (MyComment)实体类
 *
 * @author makejava
 * @since 2021-11-13 11:53:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyComment implements Serializable {

    private static final long serialVersionUID = 454292928361574917L;

    @TableId(type = IdType.AUTO)
    private Integer comId;
    
    private Integer blogId;
    
    private Integer comOwner;

    @TableField(exist = false)
    private MyUser owner;
    
    private String comBody;

    @TableField(fill = FieldFill.INSERT)
    private Date comTime;

    @TableLogic(value = "1", delval = "0")
    private Integer comState;

    @TableField(exist = false)
    private List<MyRely> myRelies;
}