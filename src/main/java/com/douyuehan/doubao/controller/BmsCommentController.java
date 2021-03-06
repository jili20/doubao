package com.douyuehan.doubao.controller;

import com.douyuehan.doubao.common.api.ApiResult;
import com.douyuehan.doubao.model.vo.CommentVO;
import com.douyuehan.doubao.service.IBmsCommentService;
import com.douyuehan.doubao.service.IUmsUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

//    @PostMapping("/add_comment")
//    public ApiResult<BmsComment> add_comment(@RequestHeader(value = USER_NAME) String userName,
//                                             @RequestBody CommentDTO dto) {
//        UmsUser user = umsUserService.getUserByUsername(userName);
//        BmsComment comment = bmsCommentService.create(dto, user);
//        return ApiResult.success(comment);
//    }
}

