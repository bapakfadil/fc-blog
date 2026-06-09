package com.fastcampus.blog.controllers;

import com.fastcampus.blog.properties.SecretProperties;
import com.fastcampus.blog.requests.auths.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SecretProperties secretProperties;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void login_givenValidCredentials_shouldReturnToken() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .username("${USERNAME}")
                .password("${PASSWORD}")
                .build();

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    void login_givenInvalidPassword_shouldReturnForbidden() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .username("satpam")
                .password("wrongpassword")
                .build();

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void login_givenInvalidUsername_shouldReturnForbidden() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .username("wronguser")
                .password("password")
                .build();

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void login_givenInvalidPayload_shouldReturnBadRequest() throws Exception {
        // username too short, password too short
        LoginRequest request = LoginRequest.builder()
                .username("u")
                .password("p")
                .build();

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessages").exists());
    }
}
