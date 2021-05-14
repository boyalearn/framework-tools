package com.framework.tool.security.service;

import com.framework.tool.security.entity.Authority;
import com.framework.tool.security.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    private static Map<String, UserInfo> USERS = new HashMap<>();

    static {
        UserInfo userInfo = new UserInfo();
        userInfo.setPassword("123");
        userInfo.setUsername("xiaoming");
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        authorityList.add(new Authority("ADMIN"));
        authorityList.add(new Authority("COMMON_USER"));
        userInfo.setAuthorities(authorityList);
        USERS.put(userInfo.getUsername(), userInfo);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return USERS.get(username);
    }
}
