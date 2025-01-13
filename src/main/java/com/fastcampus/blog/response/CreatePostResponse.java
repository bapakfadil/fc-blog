package com.fastcampus.blog.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostResponse {
    private String title;
    private String slug;
    private String body;
    private Long publishedAt;
    private Long commentCount;
}
