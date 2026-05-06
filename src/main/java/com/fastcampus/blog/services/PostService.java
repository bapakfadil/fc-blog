package com.fastcampus.blog.services;

import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.repositories.PostRepository;
import com.fastcampus.blog.requests.CreatePostRequest;
import com.fastcampus.blog.responses.CreatePostResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

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
    public Post getPost(String slug) {
        return postRepository.findPostBySlugAndIsDeleted(slug, false).orElse(null);
    }

    // Create new post
    public CreatePostResponse createPost(CreatePostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setSlug(request.getSlug());
        post.setBody(request.getBody());
        post.setCreatedAt(Instant.now());
        post.setCommentCount(0);
        post = postRepository.save(post);

        return CreatePostResponse.builder()
                .title(post.getTitle())
                .slug(post.getSlug())
                .body(post.getBody())
                .createdAt(post.getCreatedAt())
                .commentCount(post.getCommentCount())
                .build();
    }

    // TODO : Lanjutkan 1-8-2 menit ke 6

    // Update post
    public Post updatePost(String slug, Post updatedPost) {
        Post targetPost = postRepository.findPostBySlugAndIsDeleted(slug, false).orElse(null);
        if (targetPost == null) {
            return null;
        }
        targetPost.setUpdatedAt(Instant.now());
        targetPost.setTitle(updatedPost.getTitle());
        targetPost.setBody(updatedPost.getBody());
        targetPost.setSlug(updatedPost.getSlug());
        return postRepository.save(targetPost);
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
