package com.douyuehan.doubao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyuehan.doubao.mapper.BmsTopicTagMapper;
import com.douyuehan.doubao.model.entity.BmsTopicTag;
import com.douyuehan.doubao.service.IBmsTopicTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author bing  @create 2021/3/4-4:59 下午
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class IBmsTopicTagServiceImpl extends ServiceImpl<BmsTopicTagMapper, BmsTopicTag> implements IBmsTopicTagService {

    /**
     * 查询帖子标签中间表
     * 根据帖子 id 查询标签集合
     * @param topicId TopicId
     * @return
     */
    @Override
    public List<BmsTopicTag> selectByTopicId(String topicId) {
        QueryWrapper<BmsTopicTag> wrapper = new QueryWrapper<>();
        // 查询中间表：根据帖子 id 查询标签集合
        wrapper.lambda().eq(BmsTopicTag::getTopicId, topicId);
        return this.baseMapper.selectList(wrapper);
    }
}

