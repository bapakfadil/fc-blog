package com.fastcampus.blog.responses.comments;

import com.fastcampus.blog.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentResponse {
    private String name;
    private String email;
    private String body;
    private Instant createdAt;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post{
        private String title;
        private String slug;
    }
}
