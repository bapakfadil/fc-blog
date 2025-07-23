package com.bapakfadil.blog.requests.post;

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
public class UpdatePostBySlugRequest {
    @Size(min = 2, message = "Min 2 characters")
    @NotNull
    private String title;

    @Size(min = 10, message = "Min 10 characters")
    @NotNull
    private String body;

    @Size(min = 2, message = "Min 2 characters")
    @NotNull
    private String slug;
}
