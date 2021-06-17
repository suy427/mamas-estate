package com.sondahum.mamas.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurities {
    public static boolean authenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            return false;

        return AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())
                && authentication.isAuthenticated();
    }
}
