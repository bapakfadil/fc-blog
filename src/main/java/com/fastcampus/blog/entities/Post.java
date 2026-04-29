package com.fastcampus.blog.entities;

import lombok.Data;

import java.time.Instant;

@Data
public class Post {
    private Integer id;
    private String title;
    private String body;
    private String slug;
    private boolean isPublished;
    private boolean isDeleted;
    private Instant createdAt;
    private Instant publishedAt;
    private Instant deletedAt;
    private Instant updatedAt;

    public Post(Integer id, String title, String body, String slug) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.slug = slug;
    }
}
