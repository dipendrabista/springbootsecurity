package com.punojsoft.springbootsecurity.bootsecurity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Secured("ROLE_ADMIN")
    @GetMapping
    public String index() {
        return "post list";
    }

    @GetMapping("/add")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public String add(Principal principal) {
        System.out.println("currently logged in user :" + principal);
        return "post add";
    }
}
