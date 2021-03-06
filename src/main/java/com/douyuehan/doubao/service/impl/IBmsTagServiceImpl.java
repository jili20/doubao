package com.douyuehan.doubao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyuehan.doubao.mapper.BmsTagMapper;
import com.douyuehan.doubao.model.entity.BmsPost;
import com.douyuehan.doubao.model.entity.BmsTag;
import com.douyuehan.doubao.service.IBmsTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author bing  @create 2021/3/4-4:59 下午
 */
@Service
public class IBmsTagServiceImpl extends ServiceImpl<BmsTagMapper, BmsTag> implements IBmsTagService {

    @Autowired
    private com.douyuehan.doubao.service.IBmsTopicTagService IBmsTopicTagService;

    @Autowired
    private com.douyuehan.doubao.service.IBmsPostService IBmsPostService;


    @Override
    public List<BmsTag> insertTags(List<String> tagNames) {
        // 传进来的是标签列表
        List<BmsTag> tagList = new ArrayList<>();
        for (String tagName : tagNames) {
            // 拿传入的标签，查标签信息表，看这个标签是否已存在
            BmsTag tag = this.baseMapper.selectOne(new LambdaQueryWrapper<BmsTag>().eq(BmsTag::getName, tagName));
            // 如果等于空，数据库里没有，就是新的标签
            if (tag == null) {
                // 就创建标签对象，把新标签插入标签数据表
                tag = BmsTag.builder().name(tagName).build();
                this.baseMapper.insert(tag);
            } else {
                // 如果传入的标签已存在，就将该标签的话题数 +1
                tag.setTopicCount(tag.getTopicCount() + 1);
                // 更新标签
                this.baseMapper.updateById(tag);
            }
            tagList.add(tag);
        }
        return tagList;
    }

    @Override
    public Page<BmsPost> selectTopicsByTagId(Page<BmsPost> topicPage, String id) {
        // 获取关联的话题ID
        Set<String> ids = IBmsTopicTagService.selectTopicIdsByTagId(id);
        LambdaQueryWrapper<BmsPost> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(BmsPost::getId, ids);

        return IBmsPostService.page(topicPage, wrapper);
    }

}
