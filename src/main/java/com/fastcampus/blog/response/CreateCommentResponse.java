package com.fastcampus.blog.response;

import com.fastcampus.blog.entity.Post;
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
