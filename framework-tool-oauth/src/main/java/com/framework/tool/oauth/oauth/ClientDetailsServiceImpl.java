package com.framework.tool.oauth.oauth;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ClientDetailsServiceImpl implements ClientDetailsService {
    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {

        if ("weixin".equals(s) || "app".equals(s) || "mini_app".equals(s) || "global".equals(s)) {
            BaseClientDetails credential = new BaseClientDetails();
            credential.setClientId(s);
            credential.setClientSecret("testpassword");
            Set<String> authorizedTypes = new HashSet<>();
            authorizedTypes.add("authorization_code");
            authorizedTypes.add("client_credentials");
            authorizedTypes.add("password");
            authorizedTypes.add("refresh_token");
            credential.setAuthorizedGrantTypes(authorizedTypes);
            credential.setAccessTokenValiditySeconds(3600 * 24);
            credential.setRefreshTokenValiditySeconds(3600 * 24 * 30);
            Set<String> redirectUrls = new HashSet<>();
            redirectUrls.add("http://localhost:8080");
            Set<String> scope = new HashSet<String>();
            scope.add("all");
            credential.setScope(scope);
            return credential;
        }
        return null;
    }
}
