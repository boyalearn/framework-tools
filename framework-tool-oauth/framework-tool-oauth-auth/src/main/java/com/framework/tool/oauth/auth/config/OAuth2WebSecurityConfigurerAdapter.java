package com.framework.tool.oauth.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class OAuth2WebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {


    /**
     * 支持授权码的方式
     *
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/favicon.ico",
                        "/login",
                        "/customer/oauth/token",
                        "/authentication/**",
                        "/oauth/token",
                        "/actuator/**",
                        "/oauth/authorize"
                )
                .permitAll() // 允许匿名访问的地址
                .and() // 使用and()方法相当于XML标签的关闭，这样允许我们继续配置父类节点。
                .authorizeRequests()
                .anyRequest()
                .authenticated() // 其它地址都需进行认证
                .and()
                .formLogin() // 启用表单登录
                .and()
                .sessionManagement()
                .and()
                .csrf()
                .disable();
    }
}
