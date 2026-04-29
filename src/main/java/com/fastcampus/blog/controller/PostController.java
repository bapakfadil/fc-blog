package com.fastcampus.blog.controller;

import com.fastcampus.blog.entities.Post;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class PostController {

    Post post1=new Post(1, "title-1", "body-1", "slug-1");
    Post post2=new Post(2, "title-2", "body-2", "slug-2");
    List<Post> posts = new ArrayList<Post>(Arrays.asList(post1,post2));

    @GetMapping("/")
    public List<Post> getPosts() {
        return posts;
    }

    @GetMapping("/{slug}")
    public Post getPost(@PathVariable String slug) {
        return posts.stream().filter(p -> p.getSlug().equals(slug)).findFirst().orElse(null);
    }

    @PostMapping("/")
    public Post createPost(@RequestBody Post post) {
        post.setCreatedAt(Instant.now());
        posts.add(post);
        return post;
    }

    @PutMapping("/{slug}")
    public Post updatePostBySlug(@PathVariable String slug, @RequestBody Post updatedPost) {
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

    @DeleteMapping("/{id}")
    public Boolean deletePostById(@PathVariable Integer id) {
        Post targetPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        if (targetPost == null) {
            return false;
        }
        targetPost.setDeletedAt(Instant.now());
        posts.remove(targetPost);
        return true;
    }

    @PutMapping("/{id}/publish")
    public Post publishPost(@PathVariable Integer id) {
        Post targetPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);;
        if (targetPost == null) {
            return null;
        }
        targetPost.setPublishedAt(Instant.now());
        targetPost.setPublished(true);
        return targetPost;
    }
}
