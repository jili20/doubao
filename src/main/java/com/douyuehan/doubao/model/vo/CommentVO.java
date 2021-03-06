package com.douyuehan.doubao.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author bing  @create 2021/3/6-11:44 上午
 */

@Data
public class CommentVO {

    private String id;

    private String content;

    private String topicId;

    private String userId;

    private String username;

    private Date createTime;

}

