package com.punojsoft.springbootsecurity.bootsecurity;

import com.punojsoft.springbootsecurity.bootsecurity.config.MailConfig;
import com.punojsoft.springbootsecurity.bootsecurity.model.Role;
import com.punojsoft.springbootsecurity.bootsecurity.model.User;
import com.punojsoft.springbootsecurity.bootsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@PropertySources(@PropertySource("classpath:mail.properties"))
public class BootsecurityApplication implements CommandLineRunner {
    public static void main(String[] args) {
        ApplicationContext ct = SpringApplication.run(BootsecurityApplication.class, args);
        MailConfig mailConfig = (MailConfig) ct.getBean("mailConfig");
        System.out.println(mailConfig);
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder().role("ROLE_ADMIN").build());

        User user = User.builder().email("punojbista55@gmail.com")
                .username("dipen")
                .password("dipen")
                .encodedPassword(new BCryptPasswordEncoder().encode("dipen"))
                .firstName("dipen")
                .lastName("Bista")
                .roles(roles).build();
        userRepository.save(user);

    }
}
