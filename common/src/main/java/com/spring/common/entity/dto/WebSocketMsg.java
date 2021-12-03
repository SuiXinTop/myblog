package com.spring.common.entity.dto;

import com.spring.common.entity.po.User;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-12-03
 * @描述
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WebSocketMsg implements Serializable {
    private static final long serialVersionUID = -81872472727189387L;

    private User user;

    private String msgContent;

    private Date msgTime;
}
