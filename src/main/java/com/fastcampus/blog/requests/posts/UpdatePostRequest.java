package com.fastcampus.blog.requests.posts;

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
public class UpdatePostRequest {
    @Size(min = 2, message = "Minimal 2 characters")
    @NotNull
    private String title;
    @Size(min = 10, message = "Minimal 10 characters")
    @NotNull
    private String body;
    @Size(min = 2, message = "Minimal 2 characters")
    @NotNull
    private String slug;
}
