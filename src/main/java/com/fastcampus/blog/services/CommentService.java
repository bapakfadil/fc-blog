package com.fastcampus.blog.services;

import com.fastcampus.blog.entities.Comment;
import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.repositories.CommentRepository;
import com.fastcampus.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    public Comment createComment(Comment comment) {
        Post post = postRepository.findPostBySlugAndIsDeleted(comment.getPost().getSlug(), false).orElse(null);
        if (post == null) {
            return null;
        }
        comment.getPost().setId(post.getId());
        comment.setCreatedAt(Instant.now());
        return commentRepository.save(comment);
    }

}
