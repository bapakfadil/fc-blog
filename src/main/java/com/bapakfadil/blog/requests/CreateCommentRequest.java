package com.bapakfadil.blog.requests;

import com.bapakfadil.blog.entities.Post;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
public class CreateCommentRequest {
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @NotNull
    private String name;

    @Size(min = 2, max = 100, message = "Email must be between 2 and 100 characters")
    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 2, max = 10_000, message = "Comment must be between 2 and 10000 characters")
    private String body;

    @NotNull
    private Post post;
}
