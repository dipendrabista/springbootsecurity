package com.punojsoft.springbootsecurity.bootsecurity.controller;

import com.punojsoft.springbootsecurity.bootsecurity.util.ApiResponse;
import com.punojsoft.springbootsecurity.bootsecurity.util.Foo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
public class SessionManagementController {
    @GetMapping("/invalid-session")
    public ResponseEntity invalidSession() {
        ApiResponse apiResponse = ApiResponse.builder()
                .date(new Date())
                .status(440)
                .code("INVALID SESSION")
                .message("Invalid Session").build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/expired-session")
    public ResponseEntity expiredSession() {
        ApiResponse apiResponse = ApiResponse.builder()
                .date(new Date())
                .code(HttpStatus.UNAUTHORIZED.toString())
                .status(440)
                .message("Expired Session").build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/session")
    public String session(HttpSession session) {
        session.setAttribute("customsession",
                Foo.builder().id(1).name("dipendra").build());
        return "session set";
    }

    @GetMapping("/session/get")
    public ResponseEntity getSession(HttpSession session) {
        Foo foo = (Foo) session.getAttribute("customsession");
        return ResponseEntity.ok(foo);
    }

}
