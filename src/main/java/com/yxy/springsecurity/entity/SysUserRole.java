package com.yxy.springsecurity.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-09 15:34:14
 */
@Data
@TableName("sys_user_role")
public class SysUserRole {
    private String userId;
    private String roleId;
}
