package com.bapakfadil.blog.services;

import com.bapakfadil.blog.entities.Comment;
import com.bapakfadil.blog.entities.Post;
import com.bapakfadil.blog.exceptions.ApiException;
import com.bapakfadil.blog.mapper.CommentMapper;
import com.bapakfadil.blog.repositories.CommentRepository;
import com.bapakfadil.blog.repositories.PostRepository;
import com.bapakfadil.blog.requests.comment.CreateCommentRequest;
import com.bapakfadil.blog.requests.comment.GetCommentByIdRequest;
import com.bapakfadil.blog.requests.comment.GetCommentsRequest;
import com.bapakfadil.blog.responses.comment.CreateCommentResponse;
import com.bapakfadil.blog.responses.comment.GetCommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    // Get All Comments
    public List<GetCommentResponse> getComments(GetCommentsRequest request) {
        Post post = postRepository.findFirstBySlugAndIsDeleted(request.getPostSlug(), false)
                .orElseThrow(() -> new ApiException("Post not found", HttpStatus.NOT_FOUND));
        PageRequest pageRequest = PageRequest.of(request.getPageNo(), request.getLimit());
        List<Comment> comments = commentRepository.findByPostId(post.getId(), pageRequest).getContent();
        List<GetCommentResponse> responses = new ArrayList<>();
        comments.forEach(comment -> responses.add(CommentMapper.INSTANCE.mapToGetCommentResponse(comment)));
        return responses;
    }

    // Get Comment by ID
    public GetCommentResponse getCommentById(GetCommentByIdRequest request) {
        Comment comment = commentRepository.findById(request.getId())
                .orElseThrow(() -> new ApiException("Comment not found", HttpStatus.NOT_FOUND));
        return CommentMapper.INSTANCE.mapToGetCommentResponse(comment);
    }

    // Create Comment
    @Transactional
    public CreateCommentResponse createComment(CreateCommentRequest request)  {
        Post post = postRepository.findFirstBySlugAndIsDeleted(request.getPost().getSlug(), false)
                .orElseThrow(() -> new ApiException("Post not found", HttpStatus.NOT_FOUND));
        Comment comment = CommentMapper.INSTANCE.map(request);

        comment.setCreatedAt(Instant.now().getEpochSecond());
        comment.getPost().setId(post.getId());
        commentRepository.save(comment);

        post.setCommentCount(post.getCommentCount() + 1);
        postRepository.save(post);

        return CommentMapper.INSTANCE.mapToCreateCommentResponse(comment);
    }
}
