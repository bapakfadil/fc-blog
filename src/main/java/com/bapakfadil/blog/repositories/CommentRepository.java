package com.bapakfadil.blog.repositories;

import com.bapakfadil.blog.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Page<Comment> findByPostId(Integer postId, Pageable pageable);
}
