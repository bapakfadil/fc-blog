package com.bapakfadil.blog.controllers;

import com.bapakfadil.blog.requests.post.CreatePostRequest;
import com.bapakfadil.blog.requests.post.GetPostBySlugRequest;
import com.bapakfadil.blog.responses.post.CreatePostResponse;
import com.bapakfadil.blog.responses.post.GetPostResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.bapakfadil.blog.entities.Post;
import com.bapakfadil.blog.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/")
    public Iterable<Post> getPost() {
        return postService.getPosts();
    }

    @GetMapping("/{slug}")
    public GetPostResponse getPostBySlug(@Valid@PathVariable String slug) {
        GetPostBySlugRequest request = GetPostBySlugRequest.builder().slug(slug).build();
        return postService.getPostsBySlug(request);
    }

    @PostMapping("/")
    public CreatePostResponse createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return postService.createPost(createPostRequest);
    }

    @PutMapping("/{slug}")
    public Post updatePostBySlug(@PathVariable String slug, @RequestBody Post sentPostByUser) {
        return postService.updatePostBySlug(slug, sentPostByUser);
    }

    @DeleteMapping("/{id}")
    public Boolean deletePostById(@PathVariable Integer id) {
        return postService.deletePostById(id); 
    }

    @PostMapping("/{id}/publish")
    public Post publishPost(@PathVariable Integer id) {
        return postService.publishPost(id);
    }
}
