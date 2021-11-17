package com.spring.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (MyCollect)实体类
 *
 * @author makejava
 * @since 2021-11-13 11:53:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyCollect implements Serializable {

    private static final long serialVersionUID = 552675684727759491L;

    @TableId(type = IdType.AUTO)
    private Integer collectId;
    
    private Integer blogId;
    
    private Integer userId;
    
    private Date collectTime;
    
    private Integer collectState;

}