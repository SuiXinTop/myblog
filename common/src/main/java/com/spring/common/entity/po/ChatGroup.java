package com.spring.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * (ChatGroup)实体类
 *
 * @author makejava
 * @since 2021-11-29 15:57:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatGroup implements Serializable {
    private static final long serialVersionUID = -98423044856384210L;

    @TableId(type = IdType.AUTO)
    private Integer msgId;
    
    private Integer userId;
    
    private String msgContent;
    
    private Date msgTime;

}