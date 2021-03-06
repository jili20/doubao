package com.douyuehan.doubao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyuehan.doubao.model.entity.BmsComment;
import com.douyuehan.doubao.model.vo.CommentVO;

import java.util.List;

/**
 * @author bing  @create 2021/3/6-11:52 上午
 */
public interface IBmsCommentService extends IService<BmsComment> {
    /**
     *
     *
     * @param topicid
     * @return {@link BmsComment}
     */
    List<CommentVO> getCommentsByTopicID(String topicid);

//    BmsComment create(CommentDTO dto, UmsUser principal);
}

