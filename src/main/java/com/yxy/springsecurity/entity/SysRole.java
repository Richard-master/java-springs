package com.yxy.springsecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-09 15:32:09
 */
@Data
@TableName("sys_role")
public class SysRole {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String roleName;
    private String roleKey;
}
