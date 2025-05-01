package com.bapakfadil.blog.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private long createdAt;
    private long publishedAt;  

}
