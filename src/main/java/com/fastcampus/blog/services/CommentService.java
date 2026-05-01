package com.fastcampus.blog.services;

import com.fastcampus.blog.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    public List<Comment> getComments(String postSlug, Integer pageNo, Integer limit) {
        List<Comment> commentsList = new ArrayList<>();
        return commentsList;
    }

    public Comment getComment(Integer id) {
        return new Comment();
    }

    public Comment createComment(Comment comment) {
        return comment;
    }

}
