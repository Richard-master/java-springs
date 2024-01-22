package com.yxy.springsecurity.congtroller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-06 14:29:47
 */
@RestController
public class HelloController {

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys->book->add')")
    public String add(){
        return "hello,add";
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys->book->list')")
    public String list(){
        return "hello,list";
    }
}
