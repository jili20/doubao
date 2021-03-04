package com.douyuehan.doubao.controller;

import com.douyuehan.doubao.common.api.ApiResult;
import com.douyuehan.doubao.model.dto.LoginDTO;
import com.douyuehan.doubao.model.dto.RegisterDTO;
import com.douyuehan.doubao.model.entity.UmsUser;
import com.douyuehan.doubao.service.IUmsUserService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.douyuehan.doubao.jwt.JwtUtil.USER_NAME;

/**
 * @author bing  @create 2021/3/2-3:08 下午
 */
@RestController
@RequestMapping("/ums/user")
public class UmsUserController extends BaseController {

    @Resource
    private IUmsUserService iUmsUserService;

    /**
     * 注册
     * @param dto
     * @return
     */
    @PostMapping("/register")
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody RegisterDTO dto) {
        UmsUser user = iUmsUserService.executeRegister(dto);
        if (ObjectUtils.isEmpty(user)) {
            return ApiResult.failed("账号注册失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", user);
        return ApiResult.success(map);
    }


    /**
     * 登录
     * @param dto
     * @return
     */
    @PostMapping("/login")
    public ApiResult<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        String token = iUmsUserService.executeLogin(dto);
        // Token 生成为空，提示 账号密码错误
        if (ObjectUtils.isEmpty(token)) {
            return ApiResult.failed("账号密码错误");
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("token", token);
        return ApiResult.success(map, "登录成功");
    }

    /**
     * 获取请求头中的用户信息
     * @param userName
     * @return
     */
    @GetMapping("/info")
    public ApiResult<UmsUser> getUser(@RequestHeader(value = USER_NAME) String userName) {
        UmsUser user = iUmsUserService.getUserByUsername(userName);
        return ApiResult.success(user);
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    public ApiResult<Object> logOut() {
        return ApiResult.success(null, "注销成功");
    }

}
