package com.bapakfadil.blog.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.bapakfadil.blog.exceptions.ApiException;
import com.bapakfadil.blog.mapper.PostMapper;
import com.bapakfadil.blog.requests.post.CreatePostRequest;
import com.bapakfadil.blog.requests.post.GetPostBySlugRequest;
import com.bapakfadil.blog.requests.post.GetPostsRequest;
import com.bapakfadil.blog.responses.post.CreatePostResponse;
import com.bapakfadil.blog.responses.post.GetPostResponse;
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
    //public Iterable<Post> getPosts() {
    //    return postRepository.findAll();
    //}

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
    public Post updatePostBySlug(String slug, Post post) {
        Post savedPost = postRepository.findFirstBySlugAndIsDeleted(slug, false).orElse(null);
        
        if (savedPost == null) {
            return null;
        }
        post.setUpdatedAt(Instant.now().getEpochSecond());
        post.setId(savedPost.getId());
        return postRepository.save(post);
    }

    // Delete Post (by ID)
    public boolean deletePostById(Integer id) {
        Post post = postRepository.findById(id).orElse(null);

        if (post == null) {
            return false;
        }
        post.setUpdatedAt(Instant.now().getEpochSecond());
        post.setDeleted(true);
        postRepository.save(post);
        return true;
    }

    // Publish Post (by ID)
    public Post publishPost(Integer id) {
        Post post = postRepository.findById(id).orElse(null);
        
        if (post == null) {
            return null;
        }
        
        post.setPublished(true);
        post.setPublishedAt(Instant.now().getEpochSecond());
        return postRepository.save(post);
    }

}
