package com.fastcampus.blog.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private Integer postId;
    private String body;
    private boolean isDeleted;
    private Instant createdAt;
    private Instant deletedAt;
    private Instant updatedAt;
}
