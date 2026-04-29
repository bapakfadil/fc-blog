package com.fastcampus.blog.services;

import com.fastcampus.blog.entities.Post;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostService {
    Post post1=new Post(1, "title-1", "body-1", "slug-1");
    Post post2=new Post(2, "title-2", "body-2", "slug-2");
    List<Post> posts = new ArrayList<Post>(Arrays.asList(post1,post2));

    // Get all posts
    public List<Post> getPosts() {
        return  posts;
    }

    // Get post by Slug
    public Post getPost(String slug) {
        return posts.stream().filter(p -> p.getSlug().equals(slug)).findFirst().orElse(null);
    }

    // Create new post
    public Post createPost(Post post) {
        post.setCreatedAt(Instant.now());
        posts.add(post);
        return post;
    }

    // Update post
    public Post updatePost(String slug, Post updatedPost) {
        Post targetPost = posts.stream().filter(p -> p.getSlug().equals(slug)).findFirst().orElse(null);
        if (targetPost == null) {
            return null;
        }
        targetPost.setUpdatedAt(Instant.now());
        targetPost.setTitle(updatedPost.getTitle());
        targetPost.setBody(updatedPost.getBody());
        targetPost.setSlug(updatedPost.getSlug());
        return targetPost;
    }

    // Delete post
    public boolean deletePost(Integer id) {
        Post targetPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        if (targetPost == null) {
            return false;
        }
        targetPost.setDeletedAt(Instant.now());
        posts.remove(targetPost);
        return true;
    }

    // Publish post
    public Post publishPost(Integer id) {
        Post targetPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);;
        if (targetPost == null) {
            return null;
        }
        targetPost.setPublishedAt(Instant.now());
        targetPost.setPublished(true);
        return targetPost;
    }
}
