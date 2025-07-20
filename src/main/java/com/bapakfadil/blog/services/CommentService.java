package com.bapakfadil.blog.services;

import com.bapakfadil.blog.entities.Comment;
import com.bapakfadil.blog.entities.Post;
import com.bapakfadil.blog.mapper.CommentMapper;
import com.bapakfadil.blog.repositories.CommentRepository;
import com.bapakfadil.blog.repositories.PostRepository;
import com.bapakfadil.blog.requests.comment.CreateCommentRequest;
import com.bapakfadil.blog.responses.comment.CreateCommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    public Iterable<Comment> getComments(String postSlug, Integer pageNo, Integer limit) {
        Post post = postRepository.findFirstBySlugAndIsDeleted(postSlug, false).orElse(null);
        if (post == null) {
            return null;
        }
        PageRequest pageRequest = PageRequest.of(pageNo, limit);
        return commentRepository.findByPostId(post.getId(), pageRequest).getContent();
    }

    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Transactional
    public CreateCommentResponse createComment(CreateCommentRequest request) {
        Post post = postRepository.findFirstBySlugAndIsDeleted(request.getPost().getSlug(), false).orElse(null);
        if (post == null) {
            return null;
        }
        Comment comment = CommentMapper.INSTANCE.mapFromCreateCommentRequest(request);

        comment.setCreatedAt(Instant.now().getEpochSecond());
        comment.getPost().setId(post.getId());
        commentRepository.save(comment);

        post.setCommentCount(post.getCommentCount() + 1);
        postRepository.save(post);

        return CommentMapper.INSTANCE.mapToCreateCommentResponse(comment);
    }
}
