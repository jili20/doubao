package com.douyuehan.doubao.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyuehan.doubao.mapper.BmsTagMapper;
import com.douyuehan.doubao.mapper.BmsTopicMapper;
import com.douyuehan.doubao.mapper.UmsUserMapper;
import com.douyuehan.doubao.model.dto.CreateTopicDTO;
import com.douyuehan.doubao.model.entity.BmsPost;
import com.douyuehan.doubao.model.entity.BmsTag;
import com.douyuehan.doubao.model.entity.BmsTopicTag;
import com.douyuehan.doubao.model.entity.UmsUser;
import com.douyuehan.doubao.model.vo.PostVO;
import com.douyuehan.doubao.model.vo.ProfileVO;
import com.douyuehan.doubao.service.IBmsPostService;
import com.douyuehan.doubao.service.IBmsTagService;
import com.douyuehan.doubao.service.IUmsUserService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author bing  @create 2021/3/4-4:28 下午
 */
@Service
public class IBmsPostServiceImpl extends ServiceImpl<BmsTopicMapper, BmsPost> implements IBmsPostService {
    @Resource
    private BmsTagMapper bmsTagMapper;

    @Resource
    private UmsUserMapper umsUserMapper;

    @Autowired
    @Lazy
    private IBmsTagService iBmsTagService;

    @Autowired
    private IUmsUserService iUmsUserService;

    @Autowired
    private com.douyuehan.doubao.service.IBmsTopicTagService IBmsTopicTagService;

    /**
     * 帖子列表
     * @param page
     * @param tab
     * @return
     */
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

    /**
     * 发贴
     * @param dto
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BmsPost create(CreateTopicDTO dto, UmsUser user) {
        // 如果有同样帖子标题 抛出异步 不能发表
        BmsPost topic1 = this.baseMapper.selectOne(new LambdaQueryWrapper<BmsPost>().eq(BmsPost::getTitle, dto.getTitle()));
        Assert.isNull(topic1, "话题已存在，请修改");

        // 没有同样的帖子 创建一个新的帖子对象
        // 封装
        BmsPost topic = BmsPost.builder()
                .userId(user.getId()) // 通过传过来的 user getId 拿到用户的 id
                .title(dto.getTitle())
                .content(EmojiParser.parseToAliases(dto.getContent()))
                .createTime(new Date())
                .build();
        this.baseMapper.insert(topic);

        // 用户积分增加
        int newScore = user.getScore() + 1;
        umsUserMapper.updateById(user.setScore(newScore));

        // 标签
        // 前端已经限定 标签不能为空，所以此处不会成立
        if (!ObjectUtils.isEmpty(dto.getTags())) {
            // 保存标签
            List<BmsTag> tags = iBmsTagService.insertTags(dto.getTags());
            // 处理标签与话题的关联
            IBmsTopicTagService.createTopicTag(topic.getId(), tags);
        }
        return topic;
    }

    @Override
    public Map<String, Object> viewTopic(String id) {
        // 创建 map，因为要返回 map
        Map<String, Object> map = new HashMap<>(16);
        // 根据帖子 id 找到帖子
        BmsPost topic = this.baseMapper.selectById(id);
        // 如果没找到帖子，抛出异常
        Assert.notNull(topic, "当前话题不存在,或已被作者删除");
        // 查询话题详情
        topic.setView(topic.getView() + 1); // 浏览量 + 1
        this.baseMapper.updateById(topic); // 插入数据库

        // emoji转码 ：如果帖子里有表情符号 做转码操作
        topic.setContent(EmojiParser.parseToUnicode(topic.getContent()));
        map.put("topic", topic);

        // 标签
        QueryWrapper<BmsTopicTag> wrapper = new QueryWrapper<>();
        // 根据帖子 id 找到所有标签的对象
        wrapper.lambda().eq(BmsTopicTag::getTopicId, topic.getId());
        Set<String> set = new HashSet<>();
        for (BmsTopicTag articleTag : IBmsTopicTagService.list(wrapper)) {
            // 遍历标签集合 得到每个标签的 id，存到 set 集合里
            set.add(articleTag.getTagId());
        }
        // 查找 id 集合对应的对象，放到 map 里返回
        List<BmsTag> tags = iBmsTagService.listByIds(set); // set =  id 集合，
        map.put("tags", tags);

        // 获取 作者 信息
        ProfileVO user = iUmsUserService.getUserProfile(topic.getUserId());
        map.put("user", user);

        return map;
    }
}


