package com.douyuehan.doubao.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.douyuehan.doubao.common.api.ApiResult;
import com.douyuehan.doubao.model.dto.CreateTopicDTO;
import com.douyuehan.doubao.model.entity.BmsPost;
import com.douyuehan.doubao.model.entity.UmsUser;
import com.douyuehan.doubao.model.vo.PostVO;
import com.douyuehan.doubao.service.IBmsPostService;
import com.douyuehan.doubao.service.IUmsUserService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.douyuehan.doubao.jwt.JwtUtil.USER_NAME;

/**
 * @author bing  @create 2021/3/4-4:23 下午
 */
@RestController
@RequestMapping("/post")
public class BmsPostController extends BaseController {

    @Resource
    private IBmsPostService iBmsPostService;

    @Resource
    private IUmsUserService umsUserService;

    /**
     * 帖子列表
     *
     * @param tab
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/list") // tab 最新 或 最热
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostVO> list = iBmsPostService.getList(new Page<>(pageNo, pageSize), tab);
        return ApiResult.success(list);
    }

    /**
     * 发布
     *
     * @param userName
     * @param dto
     * @return
     * @RequestHeader 是获取请求头中的数据, 通过指定参数 value 的值来获取请求头中指定的参数值。
     */
    @PostMapping("/create")
    public ApiResult<BmsPost> create(@RequestHeader(value = USER_NAME) String userName
            , @RequestBody CreateTopicDTO dto) {
        UmsUser user = umsUserService.getUserByUsername(userName);
        BmsPost topic = iBmsPostService.create(dto, user);
        return ApiResult.success(topic);
    }

    /**
     * 帖子详情
     * @param id
     * @return
     */
    @GetMapping()
    public ApiResult<Map<String, Object>> view(@RequestParam("id") String id) {
        Map<String, Object> map = iBmsPostService.viewTopic(id);
        return ApiResult.success(map);
    }

    /**
     * 随便看看模块信息
     * @param id
     * @return
     */
    @GetMapping("/recommend")
    public ApiResult<List<BmsPost>> getRecommend(@RequestParam("topicId") String id) {
        List<BmsPost> topics = iBmsPostService.getRecommend(id);
        return ApiResult.success(topics);
    }


    /**
     * 修改帖子
     * @param userName
     * @param post
     * @return
     */
    @PostMapping("/update")
    public ApiResult<BmsPost> update(@RequestHeader(value = USER_NAME) String userName, @Valid @RequestBody BmsPost post) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        Assert.isTrue(umsUser.getId().equals(post.getUserId()), "非本人无权修改");
        post.setModifyTime(new Date());
        post.setContent(EmojiParser.parseToAliases(post.getContent()));
        iBmsPostService.updateById(post);
        return ApiResult.success(post);
    }

    /**
     * 删除帖子
     * @param userName
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ApiResult<String> delete(@RequestHeader(value = USER_NAME) String userName, @PathVariable("id") String id) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        BmsPost byId = iBmsPostService.getById(id);
        Assert.notNull(byId, "来晚一步，话题已不存在");
        Assert.isTrue(byId.getUserId().equals(umsUser.getId()), "你为什么可以删除别人的话题？？？");
        iBmsPostService.removeById(id);
        return ApiResult.success(null, "删除成功");
    }
}

