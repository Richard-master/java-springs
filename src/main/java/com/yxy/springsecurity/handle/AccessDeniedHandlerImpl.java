package com.yxy.springsecurity.handle;

import com.alibaba.fastjson.JSON;
import com.yxy.springsecurity.utils.MyResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-09 17:25:39
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        MyResult<Object> result = new MyResult<>(HttpStatus.FORBIDDEN.value(), "当前用户无权限");

        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(JSON.toJSONString(result));
    }
}
