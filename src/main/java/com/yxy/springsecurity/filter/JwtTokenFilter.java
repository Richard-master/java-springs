package com.yxy.springsecurity.filter;

import com.mysql.cj.util.StringUtils;
import com.yxy.springsecurity.entity.LoginUser;
import com.yxy.springsecurity.utils.JwtUtil;
import com.yxy.springsecurity.utils.RedisHelper;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-07 14:23:06
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Resource
    private RedisHelper redisHelper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取Token
        String token = request.getHeader("token");
        //请求不携带token时放行
        if (StringUtils.isNullOrEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        String userId = null;
        try {
            Claims claims = JwtUtil.parseJWT("yuxingyu", token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //从redis中获取
        String redisKey = "Login:" + userId;
        LoginUser user = (LoginUser)redisHelper.get(redisKey);
        if (Objects.isNull(user)){
            throw new RuntimeException("用户未登录");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}
