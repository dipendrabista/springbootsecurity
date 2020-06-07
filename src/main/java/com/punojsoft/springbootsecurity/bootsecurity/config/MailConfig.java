package com.punojsoft.springbootsecurity.bootsecurity.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
@ToString
public class MailConfig {
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private String port;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String smtpAuth;
    @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
    private String smtpConnectionTimeOut;
    @Value("${spring.mail.properties.mail.smtp.writetimeout}")
    private String smptWriteTimeOut;
    @Value("${spring.mail.properties.mail.smtp.timeout}")
    private String smtpTimeOut;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String startTLSEnable;
}
