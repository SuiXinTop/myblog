package com.spring.common.entity.vo;

import com.spring.common.entity.po.Comment;
import com.spring.common.entity.po.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-23
 * @描述
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommentVo extends Comment implements Serializable {

    private static final long serialVersionUID = 454292928361574917L;

    private User owner;

    private List<ReplyVo> replyList;

}
