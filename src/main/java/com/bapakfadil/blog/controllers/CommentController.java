package com.bapakfadil.blog.controllers;

import com.bapakfadil.blog.entities.Comment;
import com.bapakfadil.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/")
    public Iterable<Comment> getComments(
            @RequestParam(required = false) String postSlug,
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer limit) {
       return commentService.getComments(postSlug, pageNo, limit);
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Integer id) {
        return commentService.getCommentById(id);
    }

    @PostMapping("/")
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }
}
