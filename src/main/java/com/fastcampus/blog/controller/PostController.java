package com.fastcampus.blog.controller;

import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.request.CreatePostRequest;
import com.fastcampus.blog.response.CreatePostResponse;
import com.fastcampus.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping
    public Iterable<Post> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{slug}")
    public Post getPost(@PathVariable String slug) {
        return postService.getPostBySlug(slug);
    }

    @PostMapping
    public CreatePostResponse createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return postService.createPost(createPostRequest);
    }

    @PutMapping("/{slug}")
    public Post updatePostBySlug(@PathVariable String slug, @RequestBody Post sentPost) {
        return postService.updatePostBySlug(slug, sentPost);
    }

    @DeleteMapping("/{id}")
    public Boolean deletePostById(@PathVariable Integer id) {
        return postService.deletePostId(id);
    }

    @PostMapping("/{id}/publish")
    public Post publishPost(@PathVariable Integer id) {
        return postService.publishPost(id);
    }
}
