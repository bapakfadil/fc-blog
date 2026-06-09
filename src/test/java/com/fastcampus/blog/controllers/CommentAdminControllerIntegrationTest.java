package com.fastcampus.blog.controllers;

import com.fastcampus.blog.entities.Comment;
import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.repositories.CommentRepository;
import com.fastcampus.blog.repositories.PostRepository;
import com.fastcampus.blog.requests.comments.CreateCommentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CommentAdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getComments_givenAuthenticatedUser_shouldReturnCommentList() throws Exception {
        // Arrange
        Post post = new Post();
        post.setTitle("Admin Post Title");
        post.setBody("Admin test post body contents long");
        post.setSlug("admin-test-slug");
        post.setDeleted(false);
        post.setCommentCount(0);
        post = postRepository.save(post);

        Comment comment = new Comment();
        comment.setName("Admin User");
        comment.setEmail("admin.user@example.com");
        comment.setBody("Admin comment body content");
        comment.setPost(post);
        comment.setCreatedAt(Instant.now());
        commentRepository.save(comment);

        // Act & Assert
        mockMvc.perform(get("/api/admin/comments")
                        .with(user("admin"))
                        .param("postSlug", "admin-test-slug")
                        .param("pageNo", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Admin User"))
                .andExpect(jsonPath("$[0].body").value("Admin comment body content"));
    }

    @Test
    void getComments_givenUnauthenticatedUser_shouldReturnUnauthorized() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/admin/comments")
                        .param("postSlug", "admin-test-slug")
                        .param("pageNo", "0")
                        .param("limit", "10"))
                .andExpect(status().isForbidden()); // Default Spring Security returns 403 Forbidden for anonymous users on protected endpoints
    }

    @Test
    void getComment_givenExistingIdAndAuthenticatedUser_shouldReturnComment() throws Exception {
        // Arrange
        Post post = new Post();
        post.setTitle("Post Title");
        post.setBody("Post body long enough for test");
        post.setSlug("post-slug");
        post.setDeleted(false);
        post.setCommentCount(0);
        post = postRepository.save(post);

        Comment comment = new Comment();
        comment.setName("User");
        comment.setEmail("user@example.com");
        comment.setBody("Comment body content");
        comment.setPost(post);
        comment.setCreatedAt(Instant.now());
        comment = commentRepository.save(comment);

        // Act & Assert
        mockMvc.perform(get("/api/admin/comments/" + comment.getId())
                        .with(user("admin")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("User"))
                .andExpect(jsonPath("$.body").value("Comment body content"));
    }

    @Test
    void getComment_givenNonExistingIdAndAuthenticatedUser_shouldReturnNotFound() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/admin/comments/99999")
                        .with(user("admin")))
                .andExpect(status().isNotFound());
    }

    @Test
    void createComment_givenValidPayloadAndAuthenticatedUser_shouldCreateComment() throws Exception {
        // Arrange
        Post post = new Post();
        post.setTitle("Test Post for Admin Comment");
        post.setBody("Body body body body body body");
        post.setSlug("admin-comment-post");
        post.setDeleted(false);
        post.setCommentCount(0);
        post = postRepository.save(post);

        CreateCommentRequest request = new CreateCommentRequest();
        request.setName("Jane Doe");
        request.setEmail("jane.doe@example.com");
        request.setBody("This is an admin created comment");

        Post requestPost = new Post();
        requestPost.setSlug("admin-comment-post");
        request.setPost(requestPost);

        // Act & Assert
        mockMvc.perform(post("/api/admin/comments")
                        .with(user("admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"))
                .andExpect(jsonPath("$.body").value("This is an admin created comment"));

        // Verify Database state
        Post updatedPost = postRepository.findById(post.getId()).orElseThrow();
        Assertions.assertEquals(1, updatedPost.getCommentCount());
    }
}
