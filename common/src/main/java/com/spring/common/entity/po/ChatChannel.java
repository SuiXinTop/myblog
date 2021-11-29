package com.spring.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * (ChatChannel)实体类
 *
 * @author makejava
 * @since 2021-11-29 15:57:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ChatChannel implements Serializable {
    private static final long serialVersionUID = -12618885475769821L;
    /**
     * 首次聊天，生成两条聊天通道
     */
    @TableId(type = IdType.AUTO)
    private Integer channelId;

    /**
     * 与channel_id合并未from_session
     */
    private Integer fromUser;

    private Integer toUser;

    private Date lastChatTime;

}