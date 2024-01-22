package com.yxy.springsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yxy.springsecurity.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-06 16:15:59
 */
@Mapper
public interface UserMapper extends BaseMapper<SysUser> {
    List<String> selectPermByUser(String userId);
}
