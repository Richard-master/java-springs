package com.yxy.springsecurity.handle;

import com.alibaba.fastjson.JSON;
import com.yxy.springsecurity.utils.MyResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 认证失败异常统一处理类
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-09 17:14:55
 */
@Component
public class AuthenticationFailHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        MyResult<Object> result = new MyResult<>(HttpStatus.UNAUTHORIZED.value(), "用户认证失败，请重新登陆");

        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(JSON.toJSONString(result));
    }
}
