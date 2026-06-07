package com.fastcampus.blog.controller;

import com.fastcampus.blog.requests.auths.LoginRequest;
import com.fastcampus.blog.responses.auths.LoginResponse;
import com.fastcampus.blog.services.CustomUserDetailService;
import com.fastcampus.blog.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomUserDetailService customUserDetailService;

    @PostMapping("/api/login")
    public LoginResponse loginResponse(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            String token = jwtService
                    .generateToken(customUserDetailService
                            .loadUserByUsername(loginRequest.getUsername()
                            )
                    );
            return LoginResponse.builder().token(token).build();
        }
        throw new UsernameNotFoundException("Invalid username or password");
    }
}
