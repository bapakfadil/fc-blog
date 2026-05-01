package com.fastcampus.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String body;

    @Column(unique = true)
    private String slug;
    private boolean isPublished;
    private boolean isDeleted;
    private Instant createdAt;
    private Instant publishedAt;
    private Instant deletedAt;
    private Instant updatedAt;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "post",
            orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;
    private Long commentCount;
}
