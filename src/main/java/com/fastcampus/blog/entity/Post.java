package com.fastcampus.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String title;
    private String body;

    @Column(unique = true)
    private String slug;
    private boolean isPublished;
    private boolean isDeleted;
    private Long createdAt;
    private Long publishedAt;
}
