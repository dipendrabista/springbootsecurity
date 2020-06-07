package com.punojsoft.springbootsecurity.bootsecurity.controller;

import com.punojsoft.springbootsecurity.bootsecurity.dto.UserRegistration;
import com.punojsoft.springbootsecurity.bootsecurity.model.Role;
import com.punojsoft.springbootsecurity.bootsecurity.model.User;
import com.punojsoft.springbootsecurity.bootsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Secured("ROLE_ADMIN")
    @GetMapping
    public String index() {
        return "Admin Page";
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<User> createUser(@RequestBody UserRegistration userRequestModel) {
        User user = new User();
        user.setUsername(userRequestModel.getUsername());
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setRole(userRequestModel.getRole());
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(userRequestModel.getPassword());
        user.setEncodedPassword(bCryptPasswordEncoder.encode(userRequestModel.getPassword()));

        User returnUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnUser);
    }
}
