package com.fastcampus.blog.services;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CustomUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO : ubah metode penggunaan value menjadi dari file properti nanti (latest 06.07.2026)
        return User.builder()
                .username(username)
                .password("${USER_PASSWORD}")
                .build();
    }
}
