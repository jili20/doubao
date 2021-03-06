package com.douyuehan.doubao.controller;

import com.douyuehan.doubao.common.api.ApiResult;
import com.douyuehan.doubao.model.dto.CommentDTO;
import com.douyuehan.doubao.model.entity.BmsComment;
import com.douyuehan.doubao.model.entity.UmsUser;
import com.douyuehan.doubao.model.vo.CommentVO;
import com.douyuehan.doubao.service.IBmsCommentService;
import com.douyuehan.doubao.service.IUmsUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.douyuehan.doubao.jwt.JwtUtil.USER_NAME;

/**
 * @author bing  @create 2021/3/6-12:01 下午
 */

@RestController
@RequestMapping("/comment")
public class BmsCommentController extends BaseController {

    @Resource
    private IBmsCommentService bmsCommentService;
    @Resource
    private IUmsUserService umsUserService;

    /**
     * 获取帖子 评论列表
     * @RequestParam：将请求参数绑定到你控制器的方法参数上
     * @param topicid
     * @return
     */
    @GetMapping("/get_comments")
    public ApiResult<List<CommentVO>> getCommentsByTopicID(@RequestParam(value = "topicid", defaultValue = "1") String topicid) {
        List<CommentVO> lstBmsComment = bmsCommentService.getCommentsByTopicID(topicid);
        return ApiResult.success(lstBmsComment);
    }

    /**
     * 添加评论
     * @param userName
     * @param dto
     * @return
     */
    @PostMapping("/add_comment")
    public ApiResult<BmsComment> add_comment(@RequestHeader(value = USER_NAME) String userName,
                                             @RequestBody CommentDTO dto) {
        // 通过当前登录用户名 查到用户信息
        UmsUser user = umsUserService.getUserByUsername(userName);
        BmsComment comment = bmsCommentService.create(dto, user);// 拿到帖子 内容 id 用户 id
        return ApiResult.success(comment);
    }
}

