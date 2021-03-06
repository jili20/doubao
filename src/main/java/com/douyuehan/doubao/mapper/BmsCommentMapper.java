package com.douyuehan.doubao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.douyuehan.doubao.model.entity.BmsComment;
import com.douyuehan.doubao.model.vo.CommentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author bing  @create 2021/3/6-11:42 上午
 */
@Repository
public interface BmsCommentMapper extends BaseMapper<BmsComment> {

    /** 获取一个帖子下的所有评论
     * getCommentsByTopicID
     *
     * @param topicid
     * @return
     */
    List<CommentVO> getCommentsByTopicID(@Param("topicid") String topicid);
}

