package com.bapakfadil.blog.responses.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublishPostResponse {
    private long publishedAt;
}
