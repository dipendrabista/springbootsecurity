package com.punojsoft.springbootsecurity.bootsecurity.controller;

import com.punojsoft.springbootsecurity.bootsecurity.facade.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Secured("ROLE_USER")
    @GetMapping
    public String index() {
        System.out.println("User from facade :" + authenticationFacade.getAuhAuthentication().getPrincipal());
        return "post list";
    }

    @GetMapping("/add")
    @PreAuthorize(value = "hasAnyRole('ADMIN','USER')")
    public String add(Principal principal) {
        System.out.println("currently logged in user :" + principal);
        return "post add";
    }

    @PostAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/delete")
    public String delete(Authentication authentication) {
        System.out.println("currently loggedin user :" + authentication.getPrincipal() + " Credentials :" + authentication.getCredentials());
        return "delete post";
    }
}
