package com.fastcampus.blog.controllers;

import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.repositories.PostRepository;
import com.fastcampus.blog.requests.posts.CreatePostRequest;
import com.fastcampus.blog.requests.posts.UpdatePostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getPosts_givenAuthenticatedUser_shouldReturnPosts() throws Exception {
        // Arrange
        Post post1 = new Post();
        post1.setTitle("Test Post Title One");
        post1.setBody("Test post body content one long");
        post1.setSlug("test-post-slug-one");
        post1.setDeleted(false);
        post1.setCommentCount(0);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setTitle("Test Post Title Two");
        post2.setBody("Test post body content two long");
        post2.setSlug("test-post-slug-two");
        post2.setDeleted(false);
        post2.setCommentCount(0);
        postRepository.save(post2);

        // Act & Assert
        mockMvc.perform(get("/api/admin/posts")
                        .with(user("admin"))
                        .param("pageNo", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Test Post Title One"))
                .andExpect(jsonPath("$[1].title").value("Test Post Title Two"));
    }

    @Test
    void getPosts_givenUnauthenticatedUser_shouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/admin/posts")
                        .param("pageNo", "0")
                        .param("limit", "10"))
                .andExpect(status().isForbidden());
    }

    @Test
    void getPostBySlug_givenExistingSlugAndAuthenticatedUser_shouldReturnPost() throws Exception {
        // Arrange
        Post post = new Post();
        post.setTitle("Test Post Title");
        post.setBody("Test post body content long enough");
        post.setSlug("test-post-slug");
        post.setDeleted(false);
        post.setCommentCount(0);
        post = postRepository.save(post);

        // Act & Assert
        mockMvc.perform(get("/api/admin/posts/test-post-slug")
                        .with(user("admin")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Post Title"))
                .andExpect(jsonPath("$.body").value("Test post body content long enough"))
                .andExpect(jsonPath("$.slug").value("test-post-slug"));
    }

    @Test
    void getPostBySlug_givenNonExistingSlugAndAuthenticatedUser_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/admin/posts/non-existing-slug")
                        .with(user("admin")))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPost_givenValidRequestAndAuthenticatedUser_shouldCreatePost() throws Exception {
        // Arrange
        CreatePostRequest request = CreatePostRequest.builder()
                .title("New Created Post")
                .body("This is a body of new post created via API test")
                .slug("new-created-post")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/admin/posts")
                        .with(user("admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Created Post"))
                .andExpect(jsonPath("$.body").value("This is a body of new post created via API test"))
                .andExpect(jsonPath("$.slug").value("new-created-post"));

        // Verify in DB
        Post createdPost = postRepository.findPostBySlugAndIsDeleted("new-created-post", false).orElse(null);
        Assertions.assertNotNull(createdPost);
        Assertions.assertEquals("New Created Post", createdPost.getTitle());
    }

    @Test
    void createPost_givenInvalidRequestAndAuthenticatedUser_shouldReturnBadRequest() throws Exception {
        // Arrange - title too short, body too short
        CreatePostRequest request = CreatePostRequest.builder()
                .title("A")
                .body("Short")
                .slug("a")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/admin/posts")
                        .with(user("admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessages").exists());
    }

    @Test
    void updatePost_givenExistingSlugAndValidRequestAndAuthenticatedUser_shouldUpdatePost() throws Exception {
        // Arrange
        Post post = new Post();
        post.setTitle("Original Title");
        post.setBody("Original body long enough for validation");
        post.setSlug("original-slug");
        post.setDeleted(false);
        post.setCommentCount(0);
        post = postRepository.save(post);

        UpdatePostRequest request = UpdatePostRequest.builder()
                .title("Updated Title")
                .body("Updated body long enough for validation")
                .slug("updated-slug")
                .build();

        // Act & Assert
        mockMvc.perform(put("/api/admin/posts/original-slug")
                        .with(user("admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.body").value("Updated body long enough for validation"))
                .andExpect(jsonPath("$.slug").value("updated-slug"));

        // Verify in DB
        Post updatedPost = postRepository.findPostBySlugAndIsDeleted("updated-slug", false).orElse(null);
        Assertions.assertNotNull(updatedPost);
        Assertions.assertEquals("Updated Title", updatedPost.getTitle());
    }

    @Test
    void updatePost_givenNonExistingSlugAndValidRequestAndAuthenticatedUser_shouldReturnNotFound() throws Exception {
        // Arrange
        UpdatePostRequest request = UpdatePostRequest.builder()
                .title("Updated Title")
                .body("Updated body long enough for validation")
                .slug("updated-slug")
                .build();

        // Act & Assert
        mockMvc.perform(put("/api/admin/posts/non-existing-slug")
                        .with(user("admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePost_givenExistingIdAndAuthenticatedUser_shouldDeletePost() throws Exception {
        // Arrange
        Post post = new Post();
        post.setTitle("Test Post to Delete");
        post.setBody("Test post body content long enough to delete");
        post.setSlug("delete-slug");
        post.setDeleted(false);
        post.setCommentCount(0);
        post = postRepository.save(post);

        // Act & Assert
        mockMvc.perform(delete("/api/admin/posts/" + post.getId())
                        .with(user("admin")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug").value("delete-slug"));

        // Verify in DB that it is marked deleted
        Post deletedPost = postRepository.findById(post.getId()).orElseThrow();
        Assertions.assertTrue(deletedPost.isDeleted());
        Assertions.assertNotNull(deletedPost.getDeletedAt());
    }

    @Test
    void deletePost_givenNonExistingIdAndAuthenticatedUser_shouldReturnNotFound() throws Exception {
        mockMvc.perform(delete("/api/admin/posts/99999")
                        .with(user("admin")))
                .andExpect(status().isNotFound());
    }

    @Test
    void publishPost_givenExistingIdAndAuthenticatedUser_shouldPublishPost() throws Exception {
        // Arrange
        Post post = new Post();
        post.setTitle("Test Post to Publish");
        post.setBody("Test post body content long enough to publish");
        post.setSlug("publish-slug");
        post.setDeleted(false);
        post.setPublished(false);
        post.setCommentCount(0);
        post = postRepository.save(post);

        // Act & Assert
        mockMvc.perform(put("/api/admin/posts/" + post.getId() + "/publish")
                        .with(user("admin")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.published").value(true));

        // Verify in DB
        Post publishedPost = postRepository.findById(post.getId()).orElseThrow();
        Assertions.assertTrue(publishedPost.getIsPublished());
        Assertions.assertNotNull(publishedPost.getPublishedAt());
    }

    @Test
    void publishPost_givenNonExistingIdAndAuthenticatedUser_shouldReturnNotFound() throws Exception {
        mockMvc.perform(put("/api/admin/posts/99999/publish")
                        .with(user("admin")))
                .andExpect(status().isNotFound());
    }
}
