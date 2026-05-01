package com.fastcampus.blog.controller;

import com.fastcampus.blog.entities.Comment;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @GetMapping
    public List<Comment> getComments(
            @RequestParam(required = false) String postSlug,
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer limit) {
        List<Comment> comments = new ArrayList<>();
        return comments;
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable Integer id) {
        return new Comment();
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return comment;
    }
}