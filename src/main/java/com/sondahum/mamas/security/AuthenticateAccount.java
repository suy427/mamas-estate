package com.sondahum.mamas.security;

import com.google.common.collect.Lists;
import com.sondahum.mamas.manager.application.port.in.FindManager;
import com.sondahum.mamas.manager.domain.Manager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthenticateAccount implements UserDetailsService {
    private final FindManager findManager;

    public AuthenticateAccount(FindManager findManager) {
        this.findManager = findManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager manager = findManager.byName(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found."));

        return new User(username, manager.password, Lists.newArrayList(new SimpleGrantedAuthority("USER")));
    }
}
