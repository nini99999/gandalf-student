package com.poshist.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("---------------------------------------------------------------"+userName);
        UserDetails user=userService.getUserDetailVOByName(userName);

        if (user == null) {
            System.out.println("------------------------------------------------UserName " + userName + " not found");
            throw new UsernameNotFoundException("UserName " + userName + " not found");
        }

        return user;
    }
}
