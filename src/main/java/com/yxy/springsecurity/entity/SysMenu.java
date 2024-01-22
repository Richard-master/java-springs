package com.yxy.springsecurity.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-09 15:27:51
 */
@Data
@TableName("sys_menu")
public class SysMenu {
    private String id;
    private String menuName;
    private String perms;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createTime;
    private String path;
    private String remark;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(UUID.randomUUID().toString().replace("-", ""));
        }
    }
}
