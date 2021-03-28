package com.framework.tool.oauth.oauth;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomerUserDetailsManager implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        CustomerUserDetails customerUserDetails = new CustomerUserDetails();
        customerUserDetails.setPassword(passwordEncoder.encode(customerUserDetails.getPassword()));
        return customerUserDetails;
    }

    public CustomerUserDetailsManager setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }
}
