package com.fastcampus.blog.controllers;

import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.repositories.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostPublicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Test
    void getPosts_givenPublicRequest_shouldReturnPosts() throws Exception {
        // Arrange
        Post post = new Post();
        post.setTitle("Public Post Title");
        post.setBody("Public post body content long enough");
        post.setSlug("public-post-slug");
        post.setDeleted(false);
        post.setCommentCount(0);
        postRepository.save(post);

        // Act & Assert
        mockMvc.perform(get("/api/public/posts")
                        .param("pageNo", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Public Post Title"))
                .andExpect(jsonPath("$[0].slug").value("public-post-slug"));
    }

    @Test
    void getPostBySlug_givenPublicRequest_shouldReturnPost() throws Exception {
        // Arrange
        Post post = new Post();
        post.setTitle("Specific Public Post Title");
        post.setBody("Specific public post body content long enough");
        post.setSlug("specific-slug");
        post.setDeleted(false);
        post.setCommentCount(0);
        postRepository.save(post);

        // Act & Assert
        mockMvc.perform(get("/api/public/posts/specific-slug"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Specific Public Post Title"))
                .andExpect(jsonPath("$.slug").value("specific-slug"));
    }

    @Test
    void getPostBySlug_givenNonExistingSlug_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/public/posts/non-existing-slug"))
                .andExpect(status().isNotFound());
    }
}
