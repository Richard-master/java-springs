package com.yxy.springsecurity.service;

import com.yxy.springsecurity.entity.SysUser;
import com.yxy.springsecurity.utils.MyResult;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-07 09:58:55
 */
public interface LoginService {
    MyResult<?> login(SysUser sysUser);

    MyResult<?> logout();
}
