package com.fastcampus.blog.services;

import com.fastcampus.blog.entities.Comment;
import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.exceptions.ApiException;
import com.fastcampus.blog.mapper.CommentMapper;
import com.fastcampus.blog.repositories.CommentRepository;
import com.fastcampus.blog.repositories.PostRepository;
import com.fastcampus.blog.requests.comments.CreateCommentRequest;
import com.fastcampus.blog.responses.comments.CreateCommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    public Iterable<Comment> getComments(String postSlug, Integer pageNo, Integer limit) {
        Post post = postRepository.findPostBySlugAndIsDeleted(postSlug,false).orElse(null);
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
