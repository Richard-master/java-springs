package com.yxy.springsecurity.congtroller;

import com.yxy.springsecurity.entity.SysUser;
import com.yxy.springsecurity.service.LoginService;
import com.yxy.springsecurity.utils.MyResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-07 09:55:22
 */
@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @PostMapping("/user/login")
    public MyResult<?> login(@RequestBody SysUser sysUser){
        return loginService.login(sysUser);
    }
    @PostMapping("/user/logout")
    public MyResult<?> logout(){
        return loginService.logout();
    }
}
