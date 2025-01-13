package com.fastcampus.blog.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class CreatePostRequest {

    @Size(min = 2, message = "minimal 2 karakter.")
    @NotNull
    private String title;
    @Size(min = 2, message = "minimal 2 karakter.")
    @NotNull
    private String slug;
    @Size(min = 10, message = "minimal 10 karakter.")
    @NotNull
    private String body;
}
