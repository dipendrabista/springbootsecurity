package com.punojsoft.springbootsecurity.bootsecurity.config.security.event.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.punojsoft.springbootsecurity.bootsecurity.config.security.event.OnLoginAttemptFailedEvent;
import com.punojsoft.springbootsecurity.bootsecurity.model.User;
import com.punojsoft.springbootsecurity.bootsecurity.service.UserService;
import com.punojsoft.springbootsecurity.bootsecurity.util.ApiResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginAttemptListener implements ApplicationListener<OnLoginAttemptFailedEvent> {
    @Autowired
    private UserService userService;

    @SneakyThrows
    @Override
    public void onApplicationEvent(OnLoginAttemptFailedEvent onLoginAttemptFailedEvent) {
        User user = onLoginAttemptFailedEvent.getUser();
        ApiResponse apiResponse = ApiResponse.builder().message("Too many attempt with username : " + user.getUsername() + " and password : " + user.getPassword() + " please try again later!")
                .status(405)
                .date(new Date())
                .code("TOO MANY ATTEMPT").build();
        User returUser = userService.findUserByUserName(user.getUsername());
        returUser.setAccountBlock(true);
        userService.lockUserAccount(returUser);
        onLoginAttemptFailedEvent.getResponse().getOutputStream().print(new ObjectMapper().writeValueAsString(apiResponse));
    }
}
