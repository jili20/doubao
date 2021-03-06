package com.douyuehan.doubao.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.douyuehan.doubao.common.api.ApiResult;
import com.douyuehan.doubao.model.entity.BmsPost;
import com.douyuehan.doubao.model.entity.BmsTag;
import com.douyuehan.doubao.service.IBmsTagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bing  @create 2021/3/6-4:35 下午
 */

@RestController
@RequestMapping("/tag")
public class BmsTagController extends BaseController {

    @Resource
    private IBmsTagService bmsTagService;

    // 根据某个标签，返回该标签相关的所有文章
    @GetMapping("/{name}") // 路径中传的就是 标签的名字
    public ApiResult<Map<String, Object>> getTopicsByTag(
            @PathVariable("name") String tagName,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        Map<String, Object> map = new HashMap<>(16);
        // 根据标签名字 查找标签下所有文章 分页显示
        LambdaQueryWrapper<BmsTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BmsTag::getName, tagName);
        BmsTag one = bmsTagService.getOne(wrapper);
        // 断言
        Assert.notNull(one, "话题不存在，或已被管理员删除");
        Page<BmsPost> topics = bmsTagService.selectTopicsByTagId(new Page<>(page, size), one.getId());
        // 其他热门标签
        Page<BmsTag> hotTags = bmsTagService.page(new Page<>(1, 10),
                new LambdaQueryWrapper<BmsTag>()
                        .notIn(BmsTag::getName, tagName)
                        .orderByDesc(BmsTag::getTopicCount));

        map.put("topics", topics);
        map.put("hotTags", hotTags);

        return ApiResult.success(map);
    }

}

