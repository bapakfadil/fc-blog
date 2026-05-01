package com.fastcampus.blog.services;

import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.repositories.PostRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostService {
    @Autowired
    PostRepository postRepository;

    // Get all posts
    public Iterable<Post> getPosts() {
        return postRepository.findAll();
    }

    // Get post by Slug
    public Post getPost(String slug) {
        return postRepository.findPostBySlug(slug).orElse(null);
    }

    // Create new post
    public Post createPost(Post post) {
        post.setCreatedAt(Instant.now());
        return postRepository.save(post);
    }

    // Update post
    public Post updatePost(String slug, Post updatedPost) {
        Post targetPost = postRepository.findPostBySlug(slug).orElse(null);
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
        Post targetPost = postRepository.findPostById(id);
        if (targetPost == null) {
            return false;
        }
        targetPost.setDeletedAt(Instant.now());
        postRepository.deleteById(id);
        return true;
    }

    // Publish post
    public Post publishPost(Integer id) {
        Post targetPost = postRepository.findPostById(id);;
        if (targetPost == null) {
            return null;
        }
        targetPost.setPublishedAt(Instant.now());
        targetPost.setPublished(true);
        return postRepository.save(targetPost);
    }
}
