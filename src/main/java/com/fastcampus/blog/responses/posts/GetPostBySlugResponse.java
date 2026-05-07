package com.fastcampus.blog.responses.posts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPostBySlugResponse {
    private String title;
    private String body;
    private String slug;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant publishedAt;
    private Integer commentCount;
}
