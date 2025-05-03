package com.bapakfadil.blog.services;

import java.time.Instant;

import com.bapakfadil.blog.requests.CreatePostRequest;
import com.bapakfadil.blog.responses.CreatePostResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Iterable<Post> getPosts() {
        return postRepository.findAll();    
    }

    public Post getPostsBySlug(String slug) {
        return postRepository.findFirstBySlugAndIsDeleted(slug, false).orElse(null);
    }

    public CreatePostResponse createPost(CreatePostRequest request) {
        Post post = new Post();
        post.setBody(request.getBody());
        post.setTitle(request.getTitle());
        post.setSlug(request.getSlug());
        // Untuk set value dari comment 0
        //post.setCommentCount(0L);
        post.setCreatedAt(Instant.now().getEpochSecond());

        post = postRepository.save(post);
        return new CreatePostResponse(
                post.getTitle(),
                post.getSlug(),
                post.getBody(),
                post.getCreatedAt(),
                post.getCommentCount());
    }

    public Post updatePostBySlug(String slug, Post post) {
        Post savedPost = postRepository.findFirstBySlugAndIsDeleted(slug, false).orElse(null);
        
        if (savedPost == null) {
            return null;
        }
        post.setUpdatedAt(Instant.now().getEpochSecond());
        post.setId(savedPost.getId());
        return postRepository.save(post);
    }

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
