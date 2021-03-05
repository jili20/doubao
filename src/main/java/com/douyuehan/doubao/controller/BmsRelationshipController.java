package com.douyuehan.doubao.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.douyuehan.doubao.common.api.ApiResult;
import com.douyuehan.doubao.common.exception.ApiAsserts;
import com.douyuehan.doubao.model.entity.BmsFollow;
import com.douyuehan.doubao.model.entity.UmsUser;
import com.douyuehan.doubao.service.IBmsFollowService;
import com.douyuehan.doubao.service.IUmsUserService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.douyuehan.doubao.jwt.JwtUtil.USER_NAME;

/** 关注 与 取消关注
 * @author bing  @create 2021/3/5-9:26 下午
 */

@RestController
@RequestMapping("/relationship")
public class BmsRelationshipController extends BaseController {

    @Resource
    private IBmsFollowService bmsFollowService;

    @Resource
    private IUmsUserService umsUserService;

    /**
     * 关注
     * @param userName
     * @param parentId
     * @return
     * userId 前端传过来的 被关注者的 id
     * userName 当前登录用户
     */
    @GetMapping("/subscribe/{userId}")
    public ApiResult<Object> handleFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("userId") String parentId) {
        // 首先查出当前登录用户信息
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        // 这里需要修改，如果关注人是自己，不应该显示关注按钮
        // 也可双重保险
        if (parentId.equals(umsUser.getId())) {
            ApiAsserts.fail("不能关注自己");
        }
        // 如果查询已是关注关系，提示已关注
        BmsFollow one = bmsFollowService.getOne(
                new LambdaQueryWrapper<BmsFollow>()
                        .eq(BmsFollow::getParentId, parentId)
                        .eq(BmsFollow::getFollowerId, umsUser.getId()));
        if (!ObjectUtils.isEmpty(one)) {
            ApiAsserts.fail("已关注");
        }

        BmsFollow follow = new BmsFollow();
        follow.setParentId(parentId);
        follow.setFollowerId(umsUser.getId());
        bmsFollowService.save(follow);
        return ApiResult.success(null, "关注成功");
    }

    /**
     * 取消关注
     * @param userName
     * @param parentId
     * @return
     * 前端传过来的 userId  要取消关注的人的 id = parentId
     * userName  粉丝的用户名
     */
    @GetMapping("/unsubscribe/{userId}")
    public ApiResult<Object> handleUnFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("userId") String parentId) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName); // 通过用户名得到 粉丝用户信息
        // 关注关系表中查询1条记录，条件：传入的 parentId = 表中的 parentId ； 粉丝 id = 当前登录用户 id
        BmsFollow one = bmsFollowService.getOne(
                new LambdaQueryWrapper<BmsFollow>()
                        .eq(BmsFollow::getParentId, parentId)
                        .eq(BmsFollow::getFollowerId, umsUser.getId()));
        // 如果查询的记录为空，给错误提示，不能进行取消关注操作
        if (ObjectUtils.isEmpty(one)) {
            ApiAsserts.fail("未关注！");
        }
        // 如果查询记录不为空，证明有关注关系，那么删除此记录
        bmsFollowService.remove(new LambdaQueryWrapper<BmsFollow>().eq(BmsFollow::getParentId, parentId)
                .eq(BmsFollow::getFollowerId, umsUser.getId()));
        return ApiResult.success(null, "取关成功");
    }

    /**
     * 校验 - 是否关注关系
     * @param userName
     * @param topicUserId
     * @return
     * topicUserId 前端传过来的 id，帖子作者 id
     */
    @GetMapping("/validate/{topicUserId}")
    public ApiResult<Map<String, Object>> isFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("topicUserId") String topicUserId) {
        // 查出当前登录用户信息
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        Map<String, Object> map = new HashMap<>(16); // 创建一个 map
        map.put("hasFollow", false); // 假设现在是没有关系关注
        // 当前登录用户不为空，拿前端传入的 id 和当前登录用户 id 去关注关系表查询
        if (!ObjectUtils.isEmpty(umsUser)) {
            BmsFollow one = bmsFollowService.getOne(new LambdaQueryWrapper<BmsFollow>()
                    .eq(BmsFollow::getParentId, topicUserId)
                    .eq(BmsFollow::getFollowerId, umsUser.getId()));
            // 如果查询有数据，证明已是关注关系，如果没有查到数据，证明是默认的没有关注关系
            if (!ObjectUtils.isEmpty(one)) {
                map.put("hasFollow", true);
            }
        }
        // 把结果返回
        return ApiResult.success(map);
    }
}

