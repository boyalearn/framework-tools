package com.framework.tool.oauth.auth.config;

import com.framework.tool.oauth.auth.oauth.CustomerDetailsService;
import com.framework.tool.oauth.auth.oauth.CustomerTokenEnhancer;
import com.framework.tool.oauth.auth.oauth.JsonWebTokenAccessConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

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
    public TokenStore inMemoryTokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public JsonWebTokenAccessConverter accessTokenConverter() {
        JsonWebTokenAccessConverter jsonWebTokenAccessConverter = new JsonWebTokenAccessConverter();
        jsonWebTokenAccessConverter.setSigningKey("123");
        return jsonWebTokenAccessConverter;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomerTokenEnhancer();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomerDetailsService();
    }

}
