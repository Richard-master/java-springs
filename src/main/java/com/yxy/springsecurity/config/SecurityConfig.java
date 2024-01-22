package com.yxy.springsecurity.config;

import com.yxy.springsecurity.filter.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author yxy
 * @email yuxingyu1108@qq.com
 * @time 2023-06-07 09:29:00
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private JwtTokenFilter jwtTokenFilter;
    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //不通过Session获取SecContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //允许登录接口访问
                .antMatchers("/user/login").anonymous()
                //除登录接口以外的请求全部拦截
                .anyRequest().authenticated();

        //将自定义的过滤器放到，Security过滤器前
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);

        //跨域处理
        http.cors();
        return http.build();
    }

    // WebSecurityConfigurerAdapter废弃后，获得AuthenticationManager的方法
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return  new ProviderManager();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                //关闭csrf
//                .csrf().disable()
//                //不通过Session获取SecContext
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                //允许登录接口访问
//                .antMatchers("/user/login").anonymous()
//                //除登录接口以外的请求全部拦截
//                .anyRequest().authenticated();
//
//        //将自定义的过滤器放到，Security过滤器前
//        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//    }
}
