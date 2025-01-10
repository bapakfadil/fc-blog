package com.fastcampus.blog.repository;

import com.fastcampus.blog.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post,Integer> {
    Optional<Post> findFirstBySlug(String slug);
}
