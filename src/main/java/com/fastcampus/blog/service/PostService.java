package com.fastcampus.blog.service;

import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.repository.PostRepository;
import com.fastcampus.blog.request.CreatePostRequest;
import com.fastcampus.blog.response.CreatePostResponse;
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

    public Iterable<Post> getPosts() {
        return postRepository.findAll();
    }

    public Post getPostBySlug(String slug) {
        return postRepository.findFirstBySlugAndIsDeleted(slug, false).orElse(null);
    }

    public CreatePostResponse createPost(CreatePostRequest createPostRequest) {
        Post post = new Post();

        post.setTitle(createPostRequest.getTitle());
        post.setBody(createPostRequest.getBody());
        post.setSlug(createPostRequest.getSlug());
        post.setCommentCount(0L);
        post.setCreatedAt(Instant.now().getEpochSecond());
        post = postRepository.save(post);

        return new CreatePostResponse().builder()
                .title(post.getTitle())
                .slug(post.getSlug())
                .body(post.getBody())
                .commentCount(post.getCommentCount())
                .build();
    }

    public Post updatePostBySlug(String slug, Post post) {
        Post savedPost = postRepository.findFirstBySlugAndIsDeleted(slug, false).orElse(null);
        if (savedPost == null) {
            return null;
        }
        post.setId(savedPost.getId());
        return postRepository.save(post);
    }

    public Boolean deletePostId(Integer id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            return false;
        }
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
