package com.bapakfadil.blog.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bapakfadil.blog.entities.Post;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostService {
    
    Post post1 = new Post(1, "title 1", "slug1");
    Post post2 = new Post(2, "title 2", "slug2");
    List<Post> posts = new ArrayList<Post>(Arrays.asList(post1, post2));
    
    public List<Post> getPosts() {
        return posts;    
    }

    public Post getPostsBySlug(String slug) {
        return posts.stream().filter(post -> post.getSlug().equals(slug)).findFirst().orElse(null);
    }

    public Post createPost(Post post) {
        posts.add(post);
        return post;
    }

    public Post updatePostBySlug(String slug, Post sentPostByUser) {
        Post savedPost = posts.stream().filter(p -> p.getSlug().equals(slug)).findFirst().orElse(null);
        
        if (savedPost == null) {
            return null;
        }
        
        posts.remove(savedPost);
        savedPost.setTitle(sentPostByUser.getTitle());
        savedPost.setSlug(sentPostByUser.getSlug());
        posts.add(savedPost);
        return savedPost;
    }

    public boolean deletePostById(Integer id) {
        Post savedPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);

        if (savedPost == null) {
            return false;
        }

        posts.remove(savedPost);
        return true;
    }

    public Post publishPost(Integer id) {
        Post savedPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        
        if (savedPost == null) {
            return null;
        }
        
        savedPost.setPublished(true);
        return savedPost;
    }
}
