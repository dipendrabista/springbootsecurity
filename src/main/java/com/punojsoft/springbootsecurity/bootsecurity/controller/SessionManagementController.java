package com.punojsoft.springbootsecurity.bootsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionManagementController {
    @GetMapping("/invalid-session")
    public String invalidSession() {
        return "invalid session";
    }

    @GetMapping("/expired-session")
    public String expiredSession() {
        return "expired-session";
    }
}
