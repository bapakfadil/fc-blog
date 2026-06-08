package com.fastcampus.blog.controller;

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
@RequestMapping("/api/public/posts")
public class PostPublicController {

    @Autowired
    PostService postService;

    @GetMapping
    public Iterable<GetPostsResponse> getPosts(
            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        // TODO : only published post
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
        // TODO : only published post
        return postService.getPostBySlug(slug);
    }
}
