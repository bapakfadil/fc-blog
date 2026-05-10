package com.fastcampus.blog.repositories;

import com.fastcampus.blog.entities.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page; // Import Page
import org.springframework.data.domain.Pageable; // Import Pageable
import org.springframework.data.repository.PagingAndSortingRepository; // Ubah ini
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer>, PagingAndSortingRepository<Post, Integer> {
    Optional<Post> findPostBySlugAndIsDeleted(String slug, boolean isDeleted);
    Optional<Post> findPostByIdAndIsDeleted(Integer id, boolean isDeleted);
    Page<Post> findAllByIsDeleted(boolean isDeleted, Pageable pageable);
}
