package com.douyuehan.doubao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyuehan.doubao.model.dto.CommentDTO;
import com.douyuehan.doubao.model.entity.BmsComment;
import com.douyuehan.doubao.model.entity.UmsUser;
import com.douyuehan.doubao.model.vo.CommentVO;

import java.util.List;

/**
 * @author bing  @create 2021/3/6-11:52 上午
 */
public interface IBmsCommentService extends IService<BmsComment> {
    /**
     * 获取帖子对应的评论列表
     * @param topicid
     * @return {@link BmsComment}
     */
    List<CommentVO> getCommentsByTopicID(String topicid);

    /**
     * 创建评论
     * @param dto
     * @param principal
     * @return
     */
    BmsComment create(CommentDTO dto, UmsUser principal);
}

