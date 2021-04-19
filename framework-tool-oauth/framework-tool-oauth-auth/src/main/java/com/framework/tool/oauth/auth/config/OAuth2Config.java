package com.framework.tool.oauth.auth.config;

import com.framework.tool.oauth.auth.oauth.CustomerDetailsService;
import com.framework.tool.oauth.auth.oauth.CustomerTokenConverter;
import com.framework.tool.oauth.auth.oauth.CustomerTokenEnhancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2Config {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthorizationServerConfigurerAdapter authorizationServerConfigurerAdapter() {
        return new OAuth2AuthorizationServerConfigurerAdapter();
    }


    @Bean
    public TokenStore tokenStore(AccessTokenConverter accessTokenConverter) {
        return new JwtTokenStore((JwtAccessTokenConverter) accessTokenConverter);
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        CustomerTokenConverter customerTokenConverter = new CustomerTokenConverter();
        customerTokenConverter.setKeyPair(null);
        return customerTokenConverter;
    }

    @Bean
    public TokenEnhancer tokenEnhancer(AccessTokenConverter accessTokenConverter) {
        CustomerTokenEnhancer customerTokenEnhancer = new CustomerTokenEnhancer();
        customerTokenEnhancer.setAccessTokenConverter(accessTokenConverter);
        return customerTokenEnhancer;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomerDetailsService();
    }

}
