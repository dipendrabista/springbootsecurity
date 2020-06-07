package com.punojsoft.springbootsecurity.bootsecurity.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.punojsoft.springbootsecurity.bootsecurity.util.ApiResponse;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@Component
public class CustomAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Init custom Auth Filter!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (request.getSession().getAttribute("loginAttempt") != null) {
            if ((int) request.getSession().getAttribute("loginAttempt") > 3) {
                String username = (String) servletRequest.getAttribute("username");

                ApiResponse apiResponse = ApiResponse.builder().message("Too many attempt with username : " + username + " please try again later!")
                        .status(405)
                        .date(new Date())
                        .code("TOO MANY ATTEMPT").build();
                servletResponse.getOutputStream().print(new ObjectMapper().writeValueAsString(apiResponse));
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
