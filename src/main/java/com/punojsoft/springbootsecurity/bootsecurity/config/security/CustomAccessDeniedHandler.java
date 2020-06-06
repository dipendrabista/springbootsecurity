package com.punojsoft.springbootsecurity.bootsecurity.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.punojsoft.springbootsecurity.bootsecurity.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        ApiResponse apiResponse = ApiResponse.builder()
                .date(new Date())
                .status(403)
                .message("You do not have permission to access the page")
                .code(HttpStatus.FORBIDDEN.toString()).build();
        OutputStream outputStream = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, apiResponse);
        outputStream.flush();
    }
}
