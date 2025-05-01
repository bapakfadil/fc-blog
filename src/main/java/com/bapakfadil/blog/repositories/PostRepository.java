package com.bapakfadil.blog.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bapakfadil.blog.entities.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer>{
    Optional<Post> findFirstBySlug(String slug);
        
}