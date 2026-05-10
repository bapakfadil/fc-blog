package com.fastcampus.blog.controller;

import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.requests.posts.CreatePostRequest;
import com.fastcampus.blog.requests.posts.GetPostsRequest;
import com.fastcampus.blog.requests.posts.UpdatePostRequest;
import com.fastcampus.blog.responses.posts.*;
import com.fastcampus.blog.services.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping
    public Iterable<GetPostsResponse> getPosts(
            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        GetPostsRequest getPostsRequest = GetPostsRequest.builder()
                .pageNo(pageNo)
                .limit(limit)
                .build();
        return postService.getPosts(getPostsRequest);
    }

    @GetMapping("/{slug}")
    public GetPostBySlugResponse getPostBySlug(
            @PathVariable
            @Size(min = 2, message = "Minimal 2 character.")
            String slug) {
        return postService.getPostBySlug(slug);
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
    public DeletePostResponse deletePostById(@PathVariable Integer id) {
        return postService.deletePost(id);
    }

    @PutMapping("/{id}/publish")
    public PublishPostResponse publishPostResponse(@PathVariable Integer id) {
        return postService.publishPostResponse(id);
    }
}
