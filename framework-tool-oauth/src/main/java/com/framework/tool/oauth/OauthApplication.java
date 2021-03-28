package com.framework.tool.oauth;

import com.framework.tool.oauth.oauth.CustomAccessTokenConverter;
import com.framework.tool.oauth.oauth.CustomerTokenEnhancer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class OauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class, args);
    }

    @Setter
    @Getter
    @Component
    public static class TokenKeyConfig {

        private String path;

        private String password;

        private String alias;
    }

    //@Component
    public static class TokenServiceFactory {
        private TokenKeyConfig tokenKeyConfig;
        private ClientDetailsService clientDetailsService;

        @Autowired
        public TokenServiceFactory(
                TokenKeyConfig tokenKeyConfig,
                ClientDetailsService clientDetailsService) {
            this.tokenKeyConfig = tokenKeyConfig;
            this.clientDetailsService = clientDetailsService;
        }

        @Bean
        @Primary
        public AuthorizationServerTokenServices JwtTokenService() {
            final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            // 设置自定义的TokenEnhancer, TokenConverter，用于在token中增加自定义的内容
            tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

            return defaultTokenService(tokenEnhancerChain);
        }

        @Bean
        public JwtAccessTokenConverter accessTokenConverter() {
            final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setAccessTokenConverter(new CustomAccessTokenConverter());
            // 设置token生成的公钥和密钥，密钥放在resource目录下
    /*        final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                    new ClassPathResource(tokenKeyConfig.getPath()), tokenKeyConfig.getPassword().toCharArray());
            converter.setKeyPair(keyStoreKeyFactory.getKeyPair(tokenKeyConfig.getAlias()));*/

            return converter;
        }

        @Bean
        public TokenStore tokenStore() {
            return new JwtTokenStore(accessTokenConverter());
        }

        @Bean
        public org.springframework.security.oauth2.provider.token.TokenEnhancer tokenEnhancer() {
            return new CustomerTokenEnhancer();
        }

        private AuthorizationServerTokenServices defaultTokenService(TokenEnhancerChain tokenEnhancerChain) {
            final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
            defaultTokenServices.setTokenStore(tokenStore());
            defaultTokenServices.setSupportRefreshToken(true);
            defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
            defaultTokenServices.setClientDetailsService(clientDetailsService);
            return defaultTokenServices;
        }
    }

    public static class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

        // 自定义实现的user detail，使用了用户名和密码验证，用户信息的获取
        private UsernamePasswordUserDetailService userDetailService;

        public UsernamePasswordAuthenticationProvider(UsernamePasswordUserDetailService userDetailService) {
            this.userDetailService = userDetailService;
        }

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();

            // 验证用户名和密码
            if (true) {

                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("user"));
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, authorities);
                //获取用户信息
                token.setDetails(12121);
                return token;
            }
            return null;
        }

        @Override
        public boolean supports(Class<?> aClass) {
            return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
        }
    }

    public static class UsernamePasswordUserDetailService  {
    }
}
