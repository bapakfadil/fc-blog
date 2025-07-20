package com.bapakfadil.blog.responses.post;

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
    private long createdAt;
    private long commentCount;
}
