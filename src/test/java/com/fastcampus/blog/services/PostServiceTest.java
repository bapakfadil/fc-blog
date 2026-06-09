package com.fastcampus.blog.services;

import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.exceptions.ApiException;
import com.fastcampus.blog.repositories.PostRepository;
import com.fastcampus.blog.requests.posts.CreatePostRequest;
import com.fastcampus.blog.requests.posts.GetPostsRequest;
import com.fastcampus.blog.requests.posts.UpdatePostRequest;
import com.fastcampus.blog.responses.posts.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class  PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    // check pagination to return active posts
    @Test
    void getPosts_givenValidPageRequest_shouldReturnActivePosts() {
        Post post1 = new Post();
        post1.setTitle("Title 1");
        post1.setBody("Body 1 body 1 body 1");
        post1.setSlug("slug-1");
        post1.setDeleted(false);

        Post post2 = new Post();
        post2.setTitle("Title 2");
        post2.setBody("Body 2 body 2 body 2");
        post2.setSlug("slug-2");
        post2.setDeleted(true);

        postRepository.save(post1);
        postRepository.save(post2);

        GetPostsRequest request = GetPostsRequest.builder()
                .pageNo(0)
                .limit(10)
                .build();

        List<GetPostsResponse> responses = postService.getPosts(request);
        Assertions.assertNotNull(responses);
        Assertions.assertEquals(1, responses.size());
        Assertions.assertEquals("slug-1", responses.get(0).getSlug());
    }

    // check return single post request
    @Test
    void getPostBySlug_givenExistingActivePost_shouldReturnPostResponse() {
        Post post = new Post();
        post.setTitle("Title");
        post.setBody("Body body body");
        post.setSlug("slug-test");
        post.setDeleted(false);
        postRepository.save(post);

        GetPostBySlugResponse response = postService.getPostBySlug("slug-test");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("slug-test", response.getSlug());
    }

    // check return post not found exception
    @Test
    void getPostBySlug_givenNonExistingPost_shouldThrowNotFoundException() {
        ApiException exception = Assertions.assertThrows(ApiException.class, () -> {
            postService.getPostBySlug("does-not-exist");
        });
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getErrorHttpStatus());
        Assertions.assertEquals("Post not found", exception.getErrorMessage());
    }

    // check create post request
    @Test
    void createPost_givenValidRequest_shouldSaveAndReturnPost() {
        CreatePostRequest request = CreatePostRequest.builder()
                .title("New Title")
                .body("New Body contents are long enough")
                .slug("new-post-slug")
                .build();

        CreatePostResponse response = postService.createPost(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("new-post-slug", response.getSlug());

        Optional<Post> dbPost = postRepository.findPostBySlugAndIsDeleted("new-post-slug", false);
        Assertions.assertTrue(dbPost.isPresent());
        Assertions.assertEquals("new-post-slug", dbPost.get().getSlug());
        Assertions.assertEquals(0, dbPost.get().getCommentCount());
    }

    // check update post request
    @Test
    void updatePost_givenExistingSlug_shouldUpdateAndReturnPost() {
        Post post = new Post();
        post.setTitle("Old Title");
        post.setBody("Old Body contents");
        post.setSlug("old-slug");
        post.setDeleted(false);
        postRepository.save(post);

        UpdatePostRequest request = UpdatePostRequest.builder()
                .title("Updated Title")
                .body("Updated Body contents")
                .slug("updated-slug")
                .build();

        UpdatePostResponse response = postService.updatePost("old-slug", request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("updated-slug", response.getSlug());
        Assertions.assertEquals("Updated Title", response.getTitle());

        Optional<Post> dbPost = postRepository.findPostById(post.getId());
        Assertions.assertTrue(dbPost.isPresent());
        Assertions.assertEquals("updated-slug", dbPost.get().getSlug());
    }

    // check failed to update post due to post not found
    @Test
    void updatePost_givenNonExistingSlug_shouldThrowNotFoundException() {
        UpdatePostRequest request = UpdatePostRequest.builder()
                .title("Updated Title")
                .body("Updated Body contents")
                .slug("updated-slug")
                .build();

        ApiException exception = Assertions.assertThrows(ApiException.class, () -> {
            postService.updatePost("non-existent-slug", request);
        });
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getErrorHttpStatus());
        Assertions.assertEquals("Post not found", exception.getErrorMessage());
    }

    // check delete post request
    @Test
    void deletePost_givenExistingPost_shouldMarkAsDeleted() {
        Post post = new Post();
        post.setTitle("Title");
        post.setBody("Body body body");
        post.setSlug("slug-delete");
        post.setDeleted(false);
        postRepository.save(post);

        DeletePostResponse response = postService.deletePost(post.getId());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(post.getId(), response.getId());

        Optional<Post> dbPost = postRepository.findPostById(post.getId());
        Assertions.assertTrue(dbPost.isPresent());
        Assertions.assertTrue(dbPost.get().isDeleted());
        Assertions.assertNotNull(dbPost.get().getDeletedAt());
    }

    // check failed to delete post due to post not found
    @Test
    void deletePost_givenNonExistingId_shouldThrowNotFoundException() {
        ApiException exception = Assertions.assertThrows(ApiException.class, () -> {
            postService.deletePost(99999);
        });
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getErrorHttpStatus());
        Assertions.assertEquals("Post not found", exception.getErrorMessage());
    }

    // check publish post request
    @Test
    void publishPostResponse_givenExistingActivePost_shouldPublish() {
        Post post = new Post();
        post.setTitle("Title");
        post.setBody("Body body body");
        post.setSlug("slug-publish");
        post.setDeleted(false);
        post.setPublished(false);
        postRepository.save(post);

        PublishPostResponse response = postService.publishPostResponse(post.getId());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(post.getId(), response.getId());

        Optional<Post> dbPost = postRepository.findPostById(post.getId());
        Assertions.assertTrue(dbPost.isPresent());
        Assertions.assertTrue(dbPost.get().getIsPublished());
        Assertions.assertNotNull(dbPost.get().getPublishedAt());
    }

    // check failed to publish post due to post not found (post deleted)
    @Test
    void publishPostResponse_givenDeletedPost_shouldThrowNotFoundException() {
        Post post = new Post();
        post.setTitle("Title");
        post.setBody("Body body body");
        post.setSlug("slug-publish-deleted");
        post.setDeleted(true);
        postRepository.save(post);

        ApiException exception = Assertions.assertThrows(ApiException.class, () -> {
            postService.publishPostResponse(post.getId());
        });
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getErrorHttpStatus());
        Assertions.assertEquals("Post not found", exception.getErrorMessage());
    }
}
