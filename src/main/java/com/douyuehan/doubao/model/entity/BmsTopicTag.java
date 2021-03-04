package com.douyuehan.doubao.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author bing  @create 2021/3/4-4:42 下午
 */
@Data
@TableName("bms_post_tag")
@Accessors(chain = true)
public class BmsTopicTag implements Serializable {
    private static final long serialVersionUID = 4105179910093179578L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("tag_id")
    private String tagId;

    @TableField("topic_id")
    private String topicId;
}

