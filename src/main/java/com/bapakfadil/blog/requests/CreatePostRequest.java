package com.bapakfadil.blog.requests;

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
public class CreatePostRequest {
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    @NotNull(message = "Title must not be null")
    private String title;

    @Size(min = 10, message = "Body must be at least 10 characters")
    @NotNull(message = "Body must not be null")
    private String body;

    @Size(min = 2, max = 100, message = "Slug must be between 2 and 100 characters")
    @NotNull(message = "Slug must not be null")
    private String slug;
}
