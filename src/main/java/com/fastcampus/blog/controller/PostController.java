package com.fastcampus.blog.controller;

import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.requests.posts.CreatePostRequest;
import com.fastcampus.blog.requests.posts.UpdatePostRequest;
import com.fastcampus.blog.responses.posts.CreatePostResponse;
import com.fastcampus.blog.responses.posts.GetPostBySlugResponse;
import com.fastcampus.blog.responses.posts.UpdatePostResponse;
import com.fastcampus.blog.services.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
    public GetPostBySlugResponse getPostBySlugResponse(
            @PathVariable
            @Size(min = 2, message = "Minimal 2 character.")
            String slug) {
        return postService.getPostBySlugResponse(slug);
    }

    @PostMapping
    public CreatePostResponse createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return postService.createPost(createPostRequest);
    }

    @PutMapping("/{slug}")
    public UpdatePostResponse updatePostBySlug(@PathVariable String slug, @Valid @RequestBody UpdatePostRequest updatePost) {
        return postService.updatePost(slug, updatePost);
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
