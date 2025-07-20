package com.bapakfadil.blog.responses;

import com.bapakfadil.blog.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentResponse {
    private String name;
    private String email;
    private String body;
    private Post post;
}
