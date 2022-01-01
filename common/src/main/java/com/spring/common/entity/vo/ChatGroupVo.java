package com.spring.common.entity.vo;

import com.spring.common.entity.po.ChatGroup;
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
public class ChatGroupVo extends ChatGroup implements Serializable {

    private static final long serialVersionUID = -98423044856384210L;

    private User user;
}
