package com.punojsoft.springbootsecurity.bootsecurity.controller;

import com.punojsoft.springbootsecurity.bootsecurity.config.MailConfig;
import com.punojsoft.springbootsecurity.bootsecurity.dto.UserRegistration;
import com.punojsoft.springbootsecurity.bootsecurity.model.Role;
import com.punojsoft.springbootsecurity.bootsecurity.model.User;
import com.punojsoft.springbootsecurity.bootsecurity.repository.UserRepository;
import com.punojsoft.springbootsecurity.bootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MessageSource messages;
    @Autowired
    private Environment environment;

    @Secured("ROLE_ADMIN")
    @GetMapping
    public String index() {
        return "Admin Page";
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<User> createUser(@RequestBody UserRegistration userRequestModel) {

        List<Role> roles = new ArrayList<>();
        Role role = Role.builder()
                .role(userRequestModel.getRole()).build();
        roles.add(role);

        User user = User.builder()
                .username(userRequestModel.getUsername())
                .roles(roles)
                .password(userRequestModel.getPassword())
                .email(userRequestModel.getEmail())
                .encodedPassword(bCryptPasswordEncoder.encode(userRequestModel.getPassword())).build();
        User returnUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnUser);
    }

    @PostMapping("/reset")
    public void resetPassword(final HttpServletRequest request, @RequestParam("email") String email) {
        User user = userRepository.findByEmail(email);
        String token = UUID.randomUUID().toString();
        System.out.println("random token :" + token);
        userService.createPasswordRestToken(token, user);
        mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
    }

    private String getAppUrl(HttpServletRequest request) {
        return request.getRequestURI();
    }

    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
        final String url = contextPath + "/user/changePassword?token=" + token;
//        final String message = messages.getMessage("Reset code has been set to your email", null, locale);
        return constructEmail("Reset Password", "Reset code has been set to your email" + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        MailConfig mailConfig = MailConfig.builder().build();
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(mailConfig.getUsername());
        return email;
    }
}
