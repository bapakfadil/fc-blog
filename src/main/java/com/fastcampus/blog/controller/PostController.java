package com.fastcampus.blog.controller;

import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return postService.getPost(slug);
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @PutMapping("/{slug}")
    public Post updatePostBySlug(@PathVariable String slug, @RequestBody Post updatedPost) {
        return postService.updatePost(slug, updatedPost);
    }

    @DeleteMapping("/{id}")
    public Boolean deletePostById(@PathVariable Integer id) {
        return postService.deletePost(id);
    }

    @PutMapping("/{id}/publish")
    public Post publishPost(@PathVariable Integer id) {
        return postService.publishPost(id);
    }
}
