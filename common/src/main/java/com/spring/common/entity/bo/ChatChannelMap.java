package com.spring.common.entity.bo;

import com.spring.common.entity.po.ChatChannel;
import com.spring.common.entity.po.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-29
 * @描述
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatChannelMap extends ChatChannel implements Serializable {
    private static final long serialVersionUID = -12618885475769821L;

    private User fromUserMap;

    private User toUserMap;
}
