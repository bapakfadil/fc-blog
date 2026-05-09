package com.fastcampus.blog.services;

import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.mapper.PostMapper;
import com.fastcampus.blog.repositories.PostRepository;
import com.fastcampus.blog.requests.posts.CreatePostRequest;
import com.fastcampus.blog.requests.posts.UpdatePostRequest;
import com.fastcampus.blog.responses.posts.CreatePostResponse;
import com.fastcampus.blog.responses.posts.GetPostBySlugResponse;
import com.fastcampus.blog.responses.posts.UpdatePostResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostService {
    @Autowired
    PostRepository postRepository;

    // Get all posts
    public List<Post> getPosts() {
        return postRepository.findAllByIsDeleted(false);
    }

    // Get post by Slug
    public GetPostBySlugResponse getPostBySlugResponse(String slug) {
        Post post = postRepository.findPostBySlugAndIsDeleted(slug, false).orElse(null);
        if (post == null) {
            return null;
        }
        return PostMapper.INSTANCE.getPostBySlugResponseMap(post);
    }

    // Create new post
    public CreatePostResponse createPost(CreatePostRequest request) {
        Post post = PostMapper.INSTANCE.createPostRequestMap(request);
        post.setCommentCount(0);
        post.setCreatedAt(Instant.now());
        post = postRepository.save(post);

        return PostMapper.INSTANCE.createPostResponseMap(post);
    }

    // Update post
    public UpdatePostResponse updatePost(String slug, UpdatePostRequest updatePostRequest) {
        Optional<Post> targetPostOptional = postRepository.findPostBySlugAndIsDeleted(slug, false);
        if (targetPostOptional.isEmpty()) {
            return null;
        }

        Post targetPost = targetPostOptional.get();
        PostMapper.INSTANCE.updatePostRequestMap(updatePostRequest, targetPost);
        targetPost.setUpdatedAt(Instant.now());

        Post updatedPost = postRepository.save(targetPost);

        return PostMapper.INSTANCE.updatePostResponseMap(updatedPost);
    }

    // Delete post
    public boolean deletePost(Integer id) {
        Post targetPost = postRepository.findPostByIdAndIsDeleted(id, false).orElse(null);
        if (targetPost == null) {
            return false;
        }
        targetPost.setDeleted(true);
        targetPost.setDeletedAt(Instant.now());
        postRepository.save(targetPost);
        return true;
    }

    // Publish post
    public Post publishPost(Integer id) {
        Post targetPost = postRepository.findPostByIdAndIsDeleted(id, false).orElse(null);;
        if (targetPost == null) {
            return null;
        }
        targetPost.setPublishedAt(Instant.now());
        targetPost.setPublished(true);
        return postRepository.save(targetPost);
    }
}

// TODO : Implementasi throw error ke API response di semua API requests Post
