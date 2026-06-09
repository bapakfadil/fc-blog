package com.fastcampus.blog.controllers;

import com.fastcampus.blog.controller.CommentPublicController;
import com.fastcampus.blog.entities.Comment;
import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.requests.comments.CreateCommentRequest;
import com.fastcampus.blog.responses.comments.CreateCommentResponse;
import com.fastcampus.blog.services.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CommentPublicControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentPublicController commentPublicController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentPublicController).build();
    }

    // check create comment request with valid parameter
    @Test
    void getComments_givenValidParams_shouldReturnComments() throws Exception {
        String postSlug = "test-slug";
        Integer pageNo = 0;
        Integer limit = 10;
        Comment comment1 = new Comment();
        comment1.setBody("Comment 1");
        Comment comment2 = new Comment();
        comment2.setBody("Comment 2");
        List<Comment> expectedComments = Arrays.asList(comment1, comment2);

        Mockito.when(commentService.getComments(postSlug, pageNo, limit))
                .thenReturn(expectedComments);

        mockMvc.perform(get("/api/public/comments")
                        .param("postSlug", postSlug)
                        .param("pageNo", String.valueOf(pageNo))
                        .param("limit", String.valueOf(limit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].body").value("Comment 1"))
                .andExpect(jsonPath("$[1].body").value("Comment 2"));

        Mockito.verify(commentService, Mockito.times(1)).getComments(postSlug, pageNo, limit);
    }

    // check create comment request with valid request
    @Test
    void createComment_givenValidRequest_shouldReturnResponse() throws Exception {
        CreateCommentRequest request = new CreateCommentRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setBody("Test comment");
        Post post = new Post();
        post.setSlug("test-slug");
        request.setPost(post);

        CreateCommentResponse expectedResponse = CreateCommentResponse.builder()
                .name("John Doe")
                .email("john@example.com")
                .body("Test comment")
                .build();

        Mockito.when(commentService.createComment(Mockito.any(CreateCommentRequest.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/api/public/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.body").value("Test comment"));

        Mockito.verify(commentService, Mockito.times(1)).createComment(Mockito.any(CreateCommentRequest.class));
    }
}
