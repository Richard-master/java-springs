package com.yxy.springsecurity.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-09 15:35:04
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenu {
    private String roleId;
    private String menuId;
}
