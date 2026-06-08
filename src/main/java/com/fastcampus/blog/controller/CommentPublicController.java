package com.fastcampus.blog.controller;

import com.fastcampus.blog.entities.Comment;
import com.fastcampus.blog.requests.comments.CreateCommentRequest;
import com.fastcampus.blog.responses.comments.CreateCommentResponse;
import com.fastcampus.blog.responses.comments.GetCommentResponse;
import com.fastcampus.blog.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/comments")
public class CommentPublicController {

    @Autowired
    CommentService commentService;

    @GetMapping
    public List<Comment> getComments(
            @RequestParam String postSlug,
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer limit) {
        return commentService.getComments(postSlug, pageNo, limit);
    }

    @PostMapping
    public CreateCommentResponse createComment(@Valid @RequestBody CreateCommentRequest createCommentRequest) {
        return commentService.createComment(createCommentRequest);
    }
}