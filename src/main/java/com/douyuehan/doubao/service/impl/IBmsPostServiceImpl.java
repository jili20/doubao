package com.douyuehan.doubao.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyuehan.doubao.mapper.BmsTagMapper;
import com.douyuehan.doubao.mapper.BmsTopicMapper;
import com.douyuehan.doubao.model.entity.BmsPost;
import com.douyuehan.doubao.model.entity.BmsTag;
import com.douyuehan.doubao.model.entity.BmsTopicTag;
import com.douyuehan.doubao.model.vo.PostVO;
import com.douyuehan.doubao.service.IBmsPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bing  @create 2021/3/4-4:28 下午
 */
@Service
public class IBmsPostServiceImpl extends ServiceImpl<BmsTopicMapper, BmsPost> implements IBmsPostService {
    @Resource
    private BmsTagMapper bmsTagMapper;

    @Autowired
    private com.douyuehan.doubao.service.IBmsTopicTagService IBmsTopicTagService;

    @Override
    public Page<PostVO> getList(Page<PostVO> page, String tab) {
        // 查询话题
        // 使用自定义sql查询语句，iPage 为帖子列表
        Page<PostVO> iPage = this.baseMapper.selectListAndPage(page, tab);
        // 查询话题的标签
        // 通过帖子列表，拿到所有记录，循环遍历每一个帖子
        iPage.getRecords().forEach(topic -> {
            // 根据帖子 id 拿到标签的集合
            List<BmsTopicTag> topicTags = IBmsTopicTagService.selectByTopicId(topic.getId());
            if (!topicTags.isEmpty()) { // 如果标签集合不为空
                // 拿到当前帖子对象 所有标签的 id
                List<String> tagIds = topicTags.stream().map(BmsTopicTag::getTagId).collect(Collectors.toList());
                List<BmsTag> tags = bmsTagMapper.selectBatchIds(tagIds);
                // 把拿到的 标签 id 集合 设置到 帖子的标签对象中
                topic.setTags(tags);
            }
        });
        return iPage;
    }
}


