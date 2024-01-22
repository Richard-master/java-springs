package com.yxy.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-08 08:54:06
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域路径
        registry.addMapping("/**")
                //设置允许跨域请求路径
                .allowedOriginPatterns("*")
                //是否允许cookie
                .allowCredentials(true)
                //允许请求方式
                .allowedMethods("POST","GET","PUT","DELETE")
                //设置允许的header属性
                .allowedHeaders("*")
                //跨域允许时间
                .maxAge(3600);
    }
}
