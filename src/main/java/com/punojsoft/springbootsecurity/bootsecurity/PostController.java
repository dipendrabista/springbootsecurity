package com.punojsoft.springbootsecurity.bootsecurity;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Secured("ROLE_USER")
    @GetMapping
    public String index() {
        return "post list";
    }

    @GetMapping("add")
    @PreAuthorize(value = "hasRole('USER')")
    public String add() {
        return "post add";
    }

    @PostAuthorize(value = "hasAnyRole('ADMIN','USER')")
    @GetMapping("/delete")
    public String delete() {
        return "delete post";
    }
}
