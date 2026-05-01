package com.fastcampus.blog.repositories;

import com.fastcampus.blog.entities.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    Optional<Post> findPostBySlugAndIsDeleted(String slug, boolean isDeleted);
    Optional<Post> findPostByIdAndIsDeleted(Integer id, boolean isDeleted);
    List<Post> findAllByIsDeleted(boolean isDeleted);
}
