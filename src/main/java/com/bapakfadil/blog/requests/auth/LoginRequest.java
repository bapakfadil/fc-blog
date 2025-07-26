package com.bapakfadil.blog.requests.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class LoginRequest {
    @Size(min = 2, max = 100, message = "Username must be between 2 and 100 characters")
    @NotNull(message = "Username is mandatory")
    private String username;

    @Size(min = 2, max = 100, message = "Password must be between 2 and 100 characters")
    @NotNull(message = "Password is mandatory")
    private String password;
}
