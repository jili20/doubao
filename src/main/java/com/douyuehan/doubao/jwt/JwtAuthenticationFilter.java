package com.douyuehan.doubao.jwt;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** 请求拦截器
 * @author bing  @create 2021/3/2-3:56 下午
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter { // 请求过滤器类 OncePerRequestFilter
    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override // 参数 FilterChain  过滤链
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            if(isProtectedUrl(request)) { // 如果是需要登录才能访问的请求 走这里
                // System.out.println(request.getMethod());
                if(!request.getMethod().equals("OPTIONS")) // 非 OPTIONS 方法试探的请求
                    // 调用校验 Token 方法
                    request = JwtUtil.validateTokenAndAddUserIdToHeader(request);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return; // token 校验异常时，被抛到这里，请求被打回 没有被放行
        }
        filterChain.doFilter(request, response); // 放行
    }

    private boolean isProtectedUrl(HttpServletRequest request) {
        List<String> protectedPaths = new ArrayList<String>();
        // 以下接口登录才能访问
        protectedPaths.add("/ums/user/info");
        protectedPaths.add("/ums/user/update");
        protectedPaths.add("/post/create");
        protectedPaths.add("/post/update");
        protectedPaths.add("/post/delete/*");
        protectedPaths.add("/comment/add_comment");
        protectedPaths.add("/relationship/subscribe/*");
        protectedPaths.add("/relationship/unsubscribe/*");
        protectedPaths.add("/relationship/validate/*");

        boolean bFind = false;
        for( String passedPath : protectedPaths ) {
            bFind = pathMatcher.match(passedPath, request.getServletPath());
            if( bFind ) {
                break;
            }
        }
        return bFind;
    }
}

