package com.fastcampus.blog.service;

import com.fastcampus.blog.entity.Comment;
import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.mapper.CommentMapper;
import com.fastcampus.blog.repository.CommentRepository;
import com.fastcampus.blog.repository.PostRepository;
import com.fastcampus.blog.request.CreateCommentRequest;
import com.fastcampus.blog.response.CreateCommentResponse;
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

    public Comment getComment(Integer id) {
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
