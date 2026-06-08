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
@RequestMapping("/api/admin/comments")
public class CommentAdminController {

    @Autowired
    CommentService commentService;

    @GetMapping
    public List<Comment> getComments(
            @RequestParam(required = false) String postSlug,
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer limit) {
        return commentService.getComments(postSlug, pageNo, limit);
    }

    @GetMapping("/{id}")
    public GetCommentResponse getComment(@PathVariable Integer id) {
        return commentService.getComment(id);
    }

    @PostMapping
    public CreateCommentResponse createComment(@Valid @RequestBody CreateCommentRequest createCommentRequest) {
        return commentService.createComment(createCommentRequest);
    }
}