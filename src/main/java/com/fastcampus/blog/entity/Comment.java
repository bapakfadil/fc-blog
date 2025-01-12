package com.fastcampus.blog.entity;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private String name;
    private String email;
    private Integer postId;
    private String body;
    private Long createdAt;
}
