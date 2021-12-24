package com.spring.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * (ChatMsg)实体类
 *
 * @author makejava
 * @since 2021-11-29 15:57:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMsg implements Serializable {
    private static final long serialVersionUID = -81872472727189387L;

    @TableId(type = IdType.AUTO)
    private Integer msgId;

    private Integer channelId;

    private String msgContent;

    private Date msgTime;
    /**
     * 0表示离线时发送，1表示对方在线发送
     */
    private Integer msgStatus;

}