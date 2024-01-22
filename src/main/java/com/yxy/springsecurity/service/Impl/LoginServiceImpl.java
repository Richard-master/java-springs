package com.yxy.springsecurity.service.Impl;

import com.yxy.springsecurity.entity.LoginUser;
import com.yxy.springsecurity.entity.SysUser;
import com.yxy.springsecurity.service.LoginService;
import com.yxy.springsecurity.utils.JwtUtil;
import com.yxy.springsecurity.utils.MyResult;
import com.yxy.springsecurity.utils.RedisHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-07 09:59:13
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisHelper redisHelper;
    @Override
    public MyResult<?> login(SysUser sysUser) {
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(sysUser.getUserName(), sysUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        //
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("登陆失败");
        }
        LoginUser principal = (LoginUser)authenticate.getPrincipal();
        String id = principal.getSysUser().getId();
        String jwt = JwtUtil.createJWT("yuxingyu", 3600000, id);

        //存redis
        redisHelper.set("Login:"+id,principal);
        return MyResult.success(jwt);
    }

    @Override
    public MyResult<?> logout() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser)authentication.getPrincipal();
        String redisKey = "Login:" + principal.getSysUser().getId();
        redisHelper.remove(redisKey);
        return MyResult.success();
    }
}
