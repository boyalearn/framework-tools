package com.framework.tool.oauth.resource.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TokenController {
    @Autowired
    private DefaultTokenServices defaultTokenServices;

    @Autowired
    private AccessTokenConverter accessTokenConverter;

    @GetMapping("/token")
    public Object getToken(@RequestParam("token") String value) {
        OAuth2AccessToken token = defaultTokenServices.readAccessToken(value);
        if (token == null) {
            throw new InvalidTokenException("Token was not recognised");
        }

        if (token.isExpired()) {
            throw new InvalidTokenException("Token has expired");
        }

        OAuth2Authentication authentication = defaultTokenServices.loadAuthentication(token.getValue());

        Map<String, Object> response = (Map<String, Object>) accessTokenConverter.convertAccessToken(token, authentication);

        // gh-1070
        response.put("active", true);    // Always true if token exists and not expired

        return response;
    }
}
