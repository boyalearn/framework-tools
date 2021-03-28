package com.framework.tool.oauth.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Arrays;

/**
 * OAuth服务代码:
 * 作为对用户鉴权的服务，最主要的就是对AuthorizationServerConfigurerAdapter类和WebSecurityConfigurerAdapter进行继承重写。
 * AuthorizationServerConfigurerAdapter是授权服务配置，它里面有三个方法，分别为
 * <p>
 * • ClientDetailsServiceConfigurer 定义了客户端细节服务。客户详细信息可以被初始化。
 * <p>
 * • AuthorizationServerSecurityConfigurer 在令牌端点上定义了安全约束。
 * <p>
 * • AuthorizationServerEndpointsConfigurer:定义了授权和令牌端点和令牌服务
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)
    private AccessTokenConverter accessTokenConverter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("c1")
                .secret(passwordEncoder.encode("123"))
                .redirectUris("/home")
                .authorizedGrantTypes("authorization_code", "refresh_token", "password", "implicit","client_credentials")
                .scopes("all");
    }

    /**
     * 这个配置里面需要注意的两个分别是.accessTokenConverter()和.userDetailsService();
     * <p>
     * 第一个是自定义需要获取到的用户信息（oauth自带的实体类并不能满足我们需求，所以需要自己定义一个。）
     * 第二个是定义我们去哪里获取用来和前端数据做校验的用户信息。
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter)
                .userDetailsService(userDetailsService);
        endpoints.pathMapping("/customer/oauth/authorize","/oauth/authorize");
    }
}
