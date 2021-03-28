package com.framework.tool.oauth.auth.oauth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CustomerTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        final Map<String, Object> additionalInfo = new HashMap<>();
        String license = "your license";
        boolean enable = true;//是否设置token过期 建议配置文件获取值
        long expires = 120;//Token过期期时间：分钟 建议配置文件获取值
        //配置Token超期时间：分钟
        if (enable) {
            Date expireTime = new Date(new Date().getTime() + expires);
            ((DefaultOAuth2AccessToken) accessToken).setExpiration(expireTime);
        }
        //additionalInfo 中设置业务信息 如userId等
        additionalInfo.put("license", license);
        Authentication auth = authentication.getUserAuthentication();
        if (auth != null) {
            CustomerUserDetails user = (CustomerUserDetails) auth.getPrincipal();
            if (user != null) {
                additionalInfo.put("password", user.getPassword());
            }
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
