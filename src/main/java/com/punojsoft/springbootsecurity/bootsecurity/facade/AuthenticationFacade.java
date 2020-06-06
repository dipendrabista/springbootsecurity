package com.punojsoft.springbootsecurity.bootsecurity.facade;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    @Override
    public Authentication getAuhAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
