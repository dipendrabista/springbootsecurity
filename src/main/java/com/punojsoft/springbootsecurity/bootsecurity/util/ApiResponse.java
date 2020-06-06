package com.punojsoft.springbootsecurity.bootsecurity.util;

import lombok.Data;

import java.util.Date;

@Data
public class ApiResponse {
    private String message;
    private Date date;
    private int status;
}
