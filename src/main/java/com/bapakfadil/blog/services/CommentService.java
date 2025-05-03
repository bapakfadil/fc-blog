package com.bapakfadil.blog.services;

import com.bapakfadil.blog.entities.Comment;
import com.bapakfadil.blog.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public Iterable<Comment> getComments(String postSlug, Integer pageNo, Integer limit) {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id).orElse(null);
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
