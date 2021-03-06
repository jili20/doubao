package com.douyuehan.doubao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyuehan.doubao.mapper.BmsTopicTagMapper;
import com.douyuehan.doubao.model.entity.BmsTag;
import com.douyuehan.doubao.model.entity.BmsTopicTag;
import com.douyuehan.doubao.service.IBmsTopicTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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

    @Override
    public void createTopicTag(String id, List<BmsTag> tags) { // tags 传入的标签列表
        // 先删除topicId对应的所有记录
        this.baseMapper.delete(new LambdaQueryWrapper<BmsTopicTag>().eq(BmsTopicTag::getTopicId, id));

        // 循环保存对应关联
        tags.forEach(tag -> {
            BmsTopicTag topicTag = new BmsTopicTag();
            topicTag.setTopicId(id);
            topicTag.setTagId(tag.getId());
            this.baseMapper.insert(topicTag);
        });
    }

    @Override
    public Set<String> selectTopicIdsByTagId(String id) {
        return this.baseMapper.getTopicIdsByTagId(id);
    }
}

