package com.douyuehan.doubao.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * @author bing  @create 2021/3/2-3:55 下午
 */
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    public static final long EXPIRATION_TIME = 3600_000_000L; // 1000 hour
    public static final String SECRET = "ThisIsASecret";// 自定义加密字符串 please change to your own encryption secret.
    public static final String TOKEN_PREFIX = "Bearer ";// token 前缀
    public static final String HEADER_STRING = "Authorization";
    public static final String USER_NAME = "userName";

    /**
     * 通过用户名 - 生成 Token
     *
     * @param userId
     * @return
     */
    public static String generateToken(String userId) {
        HashMap<String, Object> map = new HashMap<>();
        //you can put any data in the map
        map.put(USER_NAME, userId); // key userName,值 传进来不同的用户名
        String jwt = Jwts.builder() // builder 构建
                .setClaims(map) // 数据
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 超时时间：当前时间 + 1000小时以后
                .signWith(SignatureAlgorithm.HS512, SECRET) // 加密
                .compact();
        return jwt; //jwt前面一般都会加前缀 Bearer
    }

    /**
     * 校验 Token
     *
     * @param request
     * @return
     */
    public static HttpServletRequest validateTokenAndAddUserIdToHeader(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            try {
                Map<String, Object> body = Jwts.parser() // parser 解析
                        .setSigningKey(SECRET) // 上面自定义的密钥
                        // 解析 token 里携带的数据,把 前缀替换成空格
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody(); // 还原 map 对象
                // 创建新的 Http 请求对象，把用户信息追加到请求头 并返回
                return new CustomHttpServletRequest(request, body);
            } catch (Exception e) {
                logger.info(e.getMessage());
                throw new TokenValidationException(e.getMessage());
            }
        } else {
            throw new TokenValidationException("Missing token");
        }
    }

    /**
     * 生成新的 Http 请求对象
     * HTTP 请求类的子类
     */
    public static class CustomHttpServletRequest extends HttpServletRequestWrapper {
        private Map<String, String> claims; // 创建私有 Map

        // 把从 token 里解析出来的 map 传给此定制的子类对象
        public CustomHttpServletRequest(HttpServletRequest request, Map<String, ?> claims) {
            super(request);
            this.claims = new HashMap<>();
            // 遍历传进来的 map,加到 创建的私有 map 中
            claims.forEach((k, v) -> this.claims.put(k, String.valueOf(v)));
        }

        /**
         * 获取请求头 name
         *
         * @param name
         * @return
         */
        @Override
        public Enumeration<String> getHeaders(String name) {
            // map 是否包含 传进来的 key
            if (claims != null && claims.containsKey(name)) {
                // 如果包含 就把 key 对应的值拿到 返回回去
                return Collections.enumeration(Arrays.asList(claims.get(name)));
            }
            return super.getHeaders(name);
        }

    }

    static class TokenValidationException extends RuntimeException {
        public TokenValidationException(String msg) {
            super(msg);
        }
    }
}