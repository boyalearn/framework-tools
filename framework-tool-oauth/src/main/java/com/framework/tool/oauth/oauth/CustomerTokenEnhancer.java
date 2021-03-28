package com.framework.tool.oauth.oauth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomerTokenEnhancer extends JwtAccessTokenConverter implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>(2);

        Authentication userAuthentication = authentication.getUserAuthentication();
        // 如果是client认证，通常是服务间调取认证，token中加入admin角色
        if (authentication.isClientOnly()) {
            List<String> authorities = new ArrayList<>(1);
            authorities.add("admin");
            additionalInfo.put("authorities", authorities);

        } else {
            // 如果是用户认证，token中加入user detail，验证用户名和密码时设置的user detail
            additionalInfo.put("userInfo", userAuthentication.getDetails());

            // 将authorities转换为string类型，便于json序列化
            Set<GrantedAuthority> rolesInfo = new HashSet<>(userAuthentication.getAuthorities());
            additionalInfo.put("authorities", rolesInfo.stream().map(auth -> auth.getAuthority()).toArray());
        }

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
