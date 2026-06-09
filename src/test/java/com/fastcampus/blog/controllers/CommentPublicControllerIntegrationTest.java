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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CommentPublicControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getComments_givenExistingComments_shouldReturnCommentList() throws Exception {
        // Arrange
        Post post = new Post();
        post.setTitle("Integration Test Title");
        post.setBody("Integration test body long enough");
        post.setSlug("integration-test-slug");
        post.setDeleted(false);
        post.setCommentCount(0);
        post = postRepository.save(post);

        Comment comment1 = new Comment();
        comment1.setName("User 1");
        comment1.setEmail("user1@example.com");
        comment1.setBody("Comment 1 body content");
        comment1.setPost(post);
        comment1.setCreatedAt(Instant.now());
        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setName("User 2");
        comment2.setEmail("user2@example.com");
        comment2.setBody("Comment 2 body content");
        comment2.setPost(post);
        comment2.setCreatedAt(Instant.now());
        commentRepository.save(comment2);

        // Act & Assert
        mockMvc.perform(get("/api/public/comments")
                        .param("postSlug", "integration-test-slug")
                        .param("pageNo", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("User 1"))
                .andExpect(jsonPath("$[0].body").value("Comment 1 body content"))
                .andExpect(jsonPath("$[1].name").value("User 2"))
                .andExpect(jsonPath("$[1].body").value("Comment 2 body content"));
    }

    @Test
    void createComment_givenValidPayload_shouldCreateCommentAndIncrementPostCommentCount() throws Exception {
        // Arrange
        Post post = new Post();
        post.setTitle("Test Post for New Comment");
        post.setBody("Body body body body body body");
        post.setSlug("new-comment-post");
        post.setDeleted(false);
        post.setCommentCount(0);
        post = postRepository.save(post);

        CreateCommentRequest request = new CreateCommentRequest();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setBody("This is a new test comment");

        Post requestPost = new Post();
        requestPost.setSlug("new-comment-post");
        request.setPost(requestPost);

        // Act & Assert
        mockMvc.perform(post("/api/public/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.body").value("This is a new test comment"));

        // Verify Database state
        Post updatedPost = postRepository.findById(post.getId()).orElseThrow();
        Assertions.assertEquals(1, updatedPost.getCommentCount());
    }
}
