package com.yxy.springsecurity.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yxy.springsecurity.entity.LoginUser;
import com.yxy.springsecurity.entity.SysUser;
import com.yxy.springsecurity.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-06 16:28:09
 */
@Service
public class UserServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        SysUser sysUser = userMapper.selectOne(new QueryWrapper<SysUser>().eq("user_name", username));
        if (Objects.isNull(sysUser)){
            throw new RuntimeException("用户名或密码错误");
        }
        //查询权限信息
        List<String> perms = userMapper.selectPermByUser(sysUser.getId());

        return new LoginUser(sysUser,perms);
    }
}
