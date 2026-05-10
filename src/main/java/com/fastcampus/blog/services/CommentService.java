package com.fastcampus.blog.services;

import com.fastcampus.blog.entities.Comment;
import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.exceptions.ApiException;
import com.fastcampus.blog.mapper.CommentMapper;
import com.fastcampus.blog.repositories.CommentRepository;
import com.fastcampus.blog.repositories.PostRepository;
import com.fastcampus.blog.requests.comments.CreateCommentRequest;
import com.fastcampus.blog.responses.comments.CreateCommentResponse;
import com.fastcampus.blog.responses.comments.GetCommentResponse; // Import GetCommentResponse
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List; // Import List

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    public List<Comment> getComments(String postSlug, Integer pageNo, Integer limit) {
        Post post = postRepository
                .findPostBySlugAndIsDeleted(postSlug,false)
                .orElseThrow(() -> new ApiException("Post not found", HttpStatus.NOT_FOUND));
        PageRequest pageRequest = PageRequest.of(pageNo, limit);
        List<Comment> comments = commentRepository.findByPostId(post.getId(), pageRequest).getContent();
        return CommentMapper.INSTANCE.mapToGetCommentResponseList(comments);
    }

    public GetCommentResponse getComment(Integer id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ApiException("Comment not found", HttpStatus.NOT_FOUND));
        return CommentMapper.INSTANCE.mapToGetCommentResponse(comment);
    }

    @Transactional
    public CreateCommentResponse createComment(CreateCommentRequest createCommentRequest) {
        Post post = postRepository
                .findPostBySlugAndIsDeleted(createCommentRequest.getPost().getSlug(), false)
                .orElseThrow(() -> new ApiException("Post with slug '" + createCommentRequest.getPost().getSlug() + "' not found", HttpStatus.NOT_FOUND));
        Comment comment = CommentMapper.INSTANCE.mapToCreateCommentRequest(createCommentRequest);

        comment.getPost().setId(post.getId());
        comment.setCreatedAt(Instant.now());
        commentRepository.save(comment);

        post.setCommentCount(post.getCommentCount() + 1);
        postRepository.save(post);

        return CommentMapper.INSTANCE.mapToCreateCommentResponse(comment);
    }

}
