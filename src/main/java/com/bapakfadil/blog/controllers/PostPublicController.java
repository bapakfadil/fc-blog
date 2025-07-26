package com.bapakfadil.blog.controllers;

import com.bapakfadil.blog.requests.post.CreatePostRequest;
import com.bapakfadil.blog.requests.post.GetPostBySlugRequest;
import com.bapakfadil.blog.requests.post.GetPostsRequest;
import com.bapakfadil.blog.requests.post.UpdatePostBySlugRequest;
import com.bapakfadil.blog.responses.post.*;
import com.bapakfadil.blog.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/posts")
public class PostPublicController {

    @Autowired
    PostService postService;

    // Get All Posts
    @GetMapping
    public List<GetPostResponse> getPosts(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                          @RequestParam(required = false, defaultValue = "10") Integer limit) {
        // TODO: Only published post
        GetPostsRequest getPostsRequest = GetPostsRequest.builder()
                .pageNo(pageNo)
                .limit(limit)
                .build();
        return postService.getPosts(getPostsRequest);
    }

    // Get Post (by Slug)
    @GetMapping("/{slug}")
    public GetPostResponse getPostBySlug(@Valid @PathVariable String slug) {
        // TODO: Only published post
        GetPostBySlugRequest request = GetPostBySlugRequest.builder().slug(slug).build();
        return postService.getPostsBySlug(request);
    }
}