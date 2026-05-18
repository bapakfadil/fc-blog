package com.fastcampus.blog.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Setup security untuk CSRF (Cross-Site Request Forgery)
                .csrf(AbstractHttpConfigurer::disable)
                // Mengatur siapa saja yang dapat mengakses endpoint API
                .authorizeHttpRequests(
                        registry -> registry
                                .anyRequest()
                                .authenticated()
                )
                // Aktivasi autentikasi HTTP basic
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
