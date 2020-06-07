package com.punojsoft.springbootsecurity.bootsecurity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegistration {
    private String username;
    private String password;
    private String role;
}
