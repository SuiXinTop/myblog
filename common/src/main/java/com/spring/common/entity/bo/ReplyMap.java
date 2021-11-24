package com.spring.common.entity.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.spring.common.entity.po.Reply;
import com.spring.common.entity.po.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-23
 * @描述
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReplyMap extends Reply implements Serializable {

    private static final long serialVersionUID = 790705687502029413L;

    private CommentMap comment;

    private User owner;
}
