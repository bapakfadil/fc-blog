package com.fastcampus.blog.responses.comments;

import com.fastcampus.blog.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentResponse {
    private String name;
    private String email;
    private String body;
    private Instant createdAt;
}
