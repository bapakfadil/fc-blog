package com.bapakfadil.blog.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.bapakfadil.blog.exceptions.ApiException;
import com.bapakfadil.blog.mapper.PostMapper;
import com.bapakfadil.blog.requests.post.CreatePostRequest;
import com.bapakfadil.blog.requests.post.GetPostBySlugRequest;
import com.bapakfadil.blog.requests.post.GetPostsRequest;
import com.bapakfadil.blog.requests.post.UpdatePostBySlugRequest;
import com.bapakfadil.blog.responses.post.*;
import jakarta.persistence.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bapakfadil.blog.entities.Post;
import com.bapakfadil.blog.repositories.PostRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostService {

    @Autowired
    PostRepository postRepository;

    // Get All Post
    public List<GetPostResponse> getPosts(GetPostsRequest request) {
        List<Post> posts = postRepository.findByIsDeletedOrderByCreatedAtDesc(false);
        List<GetPostResponse> responses = new ArrayList<>();
        posts.forEach(post -> responses.add(PostMapper.INSTANCE.mapToGetPostResponse(post)));
        return responses;
    }

    // Get Post by Slug
    public GetPostResponse getPostsBySlug(GetPostBySlugRequest request) {
        Post post = postRepository.findFirstBySlugAndIsDeleted(request.getSlug(), false)
                .orElseThrow(() -> new ApiException("Post not found", HttpStatus.NOT_FOUND));
        return PostMapper.INSTANCE.mapToGetPostResponse(post);
    }

    // Create Post
    public CreatePostResponse createPost(CreatePostRequest request) {
        Post post = PostMapper.INSTANCE.mapToCreatePostResponse(request);

        // untuk set value createdAt setiap kali user membuat Post
        post.setCreatedAt(Instant.now().getEpochSecond());

        // untuk menghandle error comment_count can't be null sebelum implementasi POSTMAPPER
        //post.setCommentCount(0L);

        post = postRepository.save(post);
        return PostMapper.INSTANCE.mapToCreatePostResponse(post);
    }

    // Update Post (by Slug)
    public UpdatePostBySlugResponse updatePostBySlug(String slug, UpdatePostBySlugRequest request) {
        Post post = postRepository.findFirstBySlugAndIsDeleted(slug, false)
                .orElseThrow(() -> new ApiException("Post not found", HttpStatus.NOT_FOUND));
        post.setTitle(request.getTitle());
        post.setBody(request.getBody());
        post.setSlug(request.getSlug());
        postRepository.save(post);

        return PostMapper.INSTANCE.mapToUpdatePostBySlugResponse(post);
    }

    // Delete Post (by ID)
    public DeletePostByIdResponse deletePostById(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ApiException("Post not found", HttpStatus.NOT_FOUND));
        post.setDeleted(true);
        postRepository.save(post);
        return DeletePostByIdResponse.builder().id(id).slug(post.getSlug()).build();
    }

    // Publish Post (by ID)
    public PublishPostResponse publishPost(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ApiException("Post not found", HttpStatus.NOT_FOUND));
        post.setPublished(true);
        post.setPublishedAt(Instant.now().getEpochSecond());
        postRepository.save(post);
        return PublishPostResponse.builder().publishedAt(post.getPublishedAt()).build();
    }

}
