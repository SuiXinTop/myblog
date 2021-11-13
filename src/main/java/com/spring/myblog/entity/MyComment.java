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
    
    private String comBody;
    
    private Date comTime;
    
    private Integer comState;

}