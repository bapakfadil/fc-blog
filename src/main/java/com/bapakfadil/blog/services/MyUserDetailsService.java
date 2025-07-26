package com.bapakfadil.blog.services;

import com.bapakfadil.blog.properties.SecretProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    SecretProperties secretProperties;

    public UserDetails loadUserByUsername(String username) {
        // Untuk validasi Username
        if (username == null || !username.equals(secretProperties.getUserUsername())) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return User.builder()
                .username(secretProperties.getUserUsername())
                .password(secretProperties.getUserPassword())
                .build();
    }
}
