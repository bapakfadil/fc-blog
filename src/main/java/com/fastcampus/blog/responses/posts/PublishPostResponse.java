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
public class PublishPostResponse {
    private Integer id;
    private boolean published;
    private Instant publishedAt;
}
