package com.punojsoft.springbootsecurity.bootsecurity.service;

import com.punojsoft.springbootsecurity.bootsecurity.model.PasswordResetToken;
import com.punojsoft.springbootsecurity.bootsecurity.model.Role;
import com.punojsoft.springbootsecurity.bootsecurity.model.User;
import com.punojsoft.springbootsecurity.bootsecurity.repository.PasswordResetTokenRepository;
import com.punojsoft.springbootsecurity.bootsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + userName);
        }
        return user;
    }

    private static List<GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    public User findUserByUserName(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User lockUserAccount(User user) {
        return userRepository.save(user);
    }

    public void createPasswordRestToken(String token, User user) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }
}
