package com.douyuehan.doubao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyuehan.doubao.mapper.BmsCommentMapper;
import com.douyuehan.doubao.model.entity.BmsComment;
import com.douyuehan.doubao.model.vo.CommentVO;
import com.douyuehan.doubao.service.IBmsCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bing  @create 2021/3/6-11:54 上午
 */
@Slf4j
@Service
public class IBmsCommentServiceImpl extends ServiceImpl<BmsCommentMapper, BmsComment> implements IBmsCommentService {
    @Override
    public List<CommentVO> getCommentsByTopicID(String topicid) {
        List<CommentVO> lstBmsComment = new ArrayList<CommentVO>();
        try {
            lstBmsComment = this.baseMapper.getCommentsByTopicID(topicid);
        } catch (Exception e) {
            log.info("lstBmsComment失败");
        }
        return lstBmsComment;
    }

//    @Override
//    public BmsComment create(CommentDTO dto, UmsUser user) {
//        BmsComment comment = BmsComment.builder()
//                .userId(user.getId())
//                .content(dto.getContent())
//                .topicId(dto.getTopic_id())
//                .createTime(new Date())
//                .build();
//        this.baseMapper.insert(comment);
//        return comment;
//    }
}

