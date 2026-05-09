package com.fastcampus.blog.requests.comments;

import com.fastcampus.blog.entities.Post;
import jakarta.validation.constraints.Email;
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
public class CreateCommentRequest {
    @Size(min = 2, max = 100, message = "Name min 2 and max 100 character")
    @NotNull
    private String name;
    @NotNull
    @Email(message = "Must be a well-formed email address")
    private String email;
    @Size(min = 2, max = 1000, message = "Comment min 2 and max 1000 character")
    @NotNull
    private String body;
    @NotNull
    private Post post;
}
