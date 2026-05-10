package com.fastcampus.blog.services;

import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.exceptions.ApiException;
import com.fastcampus.blog.mapper.PostMapper;
import com.fastcampus.blog.repositories.PostRepository;
import com.fastcampus.blog.requests.posts.CreatePostRequest;
import com.fastcampus.blog.requests.posts.GetPostsRequest;
import com.fastcampus.blog.requests.posts.UpdatePostRequest;
import com.fastcampus.blog.responses.posts.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostService {
    @Autowired
    PostRepository postRepository;

    // Get all posts with pagination
    public List<GetPostsResponse> getPosts(GetPostsRequest getPostsRequest) {
        Pageable pageable = PageRequest.of(getPostsRequest.getPageNo(), getPostsRequest.getLimit());
        Page<Post> postPage = postRepository.findAllByIsDeleted(false, pageable);
        return PostMapper.INSTANCE.mapToGetPostsResponseList(postPage.getContent());
    }

    // Get post by Slug
    public GetPostBySlugResponse getPostBySlug(String slug) {
        Post post = postRepository
                .findPostBySlugAndIsDeleted(slug, false)
                .orElseThrow(() -> new ApiException("Post not found", HttpStatus.NOT_FOUND));
        return PostMapper.INSTANCE.mapToGetPostBySlugResponse(post);
    }

    // Create new post
    public CreatePostResponse createPost(CreatePostRequest request) {
        Post post = PostMapper.INSTANCE.mapToCreatePostRequest(request);
        post.setCommentCount(0);
        post.setCreatedAt(Instant.now());
        post = postRepository.save(post);

        return PostMapper.INSTANCE.mapToCreatePostResponse(post);
    }

    // Update post
    public UpdatePostResponse updatePost(String slug, UpdatePostRequest updatePostRequest) {
        Post targetPost = postRepository
                .findPostBySlugAndIsDeleted(slug, false)
                .orElseThrow(() -> new ApiException("Post not found", HttpStatus.NOT_FOUND));
        PostMapper.INSTANCE.mapToUpdatePostRequest(updatePostRequest, targetPost);
        targetPost.setUpdatedAt(Instant.now());
        Post updatedPost = postRepository.save(targetPost);
        return PostMapper.INSTANCE.mapToUpdatePostResponse(updatedPost);
    }

    // Delete post
    public DeletePostResponse deletePost(Integer id) {
        Post targetPost = postRepository
                .findPostByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new ApiException("Post not found", HttpStatus.NOT_FOUND));
        targetPost.setDeleted(true);
        targetPost.setDeletedAt(Instant.now());
        postRepository.save(targetPost);
        return PostMapper.INSTANCE.mapToDeletePostResponse(targetPost);
    }

    // Publish post
    public PublishPostResponse publishPostResponse(Integer id) {
        Post targetPost = postRepository
                .findPostByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new ApiException("Post not found", HttpStatus.NOT_FOUND));
        targetPost.setPublishedAt(Instant.now());
        targetPost.setPublished(true);
        postRepository.save(targetPost);
        return PostMapper.INSTANCE.mapToPublishPost(targetPost);
    }
}

// TODO : recheck implementasi DTO mapper pada Post dan Comment service
// TODO : rewatch 1-8-5