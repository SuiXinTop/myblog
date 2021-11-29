package com.spring.common.entity.bo;

import com.spring.common.entity.po.ChatMsg;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatMsgMap extends ChatMsg implements Serializable {

    private static final long serialVersionUID = -81872472727189387L;

    ChatChannelMap chatChannelMap;
}
