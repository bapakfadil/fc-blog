package com.fastcampus.blog.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostResponse {
    private String title;
    private String body;
    private String slug;
    private Instant createdAt;
    private Integer commentCount;
}
