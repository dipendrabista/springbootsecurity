//package com.punojsoft.springbootsecurity.bootsecurity.config.security;
//
//import com.punojsoft.springbootsecurity.bootsecurity.model.User;
//import com.punojsoft.springbootsecurity.bootsecurity.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String userName = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        User user = userService.findUserByUserName(authentication.getPrincipal().toString());
//        if (userName.equals(user.getUsername()) && password.equals(user.getPassword())) {
//            Set<GrantedAuthority> authorities = new HashSet<>();
//
//            user.getRoles().forEach(role -> {
//                authorities.add(new SimpleGrantedAuthority(role.getRole()));
//
//            });
//            return new UsernamePasswordAuthenticationToken(userName, password, authorities);
//        }
//        throw new BadCredentialsException("Username and password does not match");
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return false;
//    }
//}
