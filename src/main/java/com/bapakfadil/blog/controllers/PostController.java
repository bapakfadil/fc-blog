package com.bapakfadil.blog.controllers;

import com.bapakfadil.blog.requests.post.CreatePostRequest;
import com.bapakfadil.blog.requests.post.GetPostBySlugRequest;
import com.bapakfadil.blog.requests.post.GetPostsRequest;
import com.bapakfadil.blog.requests.post.UpdatePostBySlugRequest;
import com.bapakfadil.blog.responses.post.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.bapakfadil.blog.entities.Post;
import com.bapakfadil.blog.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    // Get All Posts
    @GetMapping("/")
    public List<GetPostResponse> getPosts(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                          @RequestParam(required = false, defaultValue = "10") Integer limit) {
        GetPostsRequest getPostsRequest = GetPostsRequest.builder()
                .pageNo(pageNo)
                .limit(limit)
                .build();
        return postService.getPosts(getPostsRequest);
    }

    // Get Post (by Slug)
    @GetMapping("/{slug}")
    public GetPostResponse getPostBySlug(@Valid@PathVariable String slug) {
        GetPostBySlugRequest request = GetPostBySlugRequest.builder().slug(slug).build();
        return postService.getPostsBySlug(request);
    }

    // Create Post
    @PostMapping("/")
    public CreatePostResponse createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return postService.createPost(createPostRequest);
    }

    // Update Post (by Slug)
    @PutMapping("/{slug}")
    public UpdatePostBySlugResponse updatePostBySlug(@PathVariable String slug, @Valid @RequestBody UpdatePostBySlugRequest updatePostBySlugRequest) {
        return postService.updatePostBySlug(slug, updatePostBySlugRequest);
    }

    // Delete Post (by ID)
    @DeleteMapping("/{id}")
    public DeletePostByIdResponse deletePostById(@PathVariable Integer id) {
        return postService.deletePostById(id);
    }

    // Publish Post (by ID)
    @PostMapping("/{id}/publish")
    public PublishPostResponse publishPost(@PathVariable Integer id) {
        return postService.publishPost(id);
    }
}
