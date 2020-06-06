package com.punojsoft.springbootsecurity.bootsecurity.util;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ApiResponse {
    private String message;
    private Date date;
    private int status;
    private String code;
}
