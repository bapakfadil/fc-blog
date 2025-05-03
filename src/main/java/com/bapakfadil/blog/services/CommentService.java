package com.bapakfadil.blog.services;

import com.bapakfadil.blog.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    public Iterable<Comment> getComments(String postSlug, Integer pageNo, Integer limit) {
        List<Comment> commentList = new ArrayList<>();
        return commentList;
    }

    public Comment getCommentById(Integer id) {
        return new Comment();
    }

    public Comment createComment(Comment comment) {
        return comment;
    }
}
