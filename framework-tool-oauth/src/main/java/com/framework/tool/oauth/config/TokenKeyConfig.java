package com.framework.tool.oauth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class TokenKeyConfig {

    private String path;

    private String password;

    private String alias;
}
