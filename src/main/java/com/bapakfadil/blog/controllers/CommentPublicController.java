package com.bapakfadil.blog.controllers;

import com.bapakfadil.blog.requests.comment.CreateCommentRequest;
import com.bapakfadil.blog.requests.comment.GetCommentByIdRequest;
import com.bapakfadil.blog.requests.comment.GetCommentsRequest;
import com.bapakfadil.blog.responses.comment.CreateCommentResponse;
import com.bapakfadil.blog.responses.comment.GetCommentResponse;
import com.bapakfadil.blog.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/comments")
public class CommentPublicController {

    @Autowired
    CommentService commentService;

    // Get All Comments
    @GetMapping("/")
    public List<GetCommentResponse> getComments(
            @RequestParam String postSlug,
            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        GetCommentsRequest request = GetCommentsRequest.builder()
                .postSlug(postSlug)
                .pageNo(pageNo)
                .limit(limit)
                .build();
        return commentService.getComments(request);
    }

    // Create Comment
    @PostMapping
    public CreateCommentResponse createComment(@Valid @RequestBody CreateCommentRequest commentRequest) {
        return commentService.createComment(commentRequest);
    }
}
