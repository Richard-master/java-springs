package com.yxy.springsecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-06 15:59:13
 */
@Data
@TableName("sys_user")
public class SysUser {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String userName;
    private String password;
}
