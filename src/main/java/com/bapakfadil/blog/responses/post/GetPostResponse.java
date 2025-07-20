package com.bapakfadil.blog.responses.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPostResponse {
    private Integer id;
    private String title;
    private String body;
    private String slug;
    private long publishedAt;
    private long commentCount;
    private boolean isPublished;
}
