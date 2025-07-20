package com.bapakfadil.blog.mapper;

import com.bapakfadil.blog.entities.Comment;
import com.bapakfadil.blog.requests.comment.CreateCommentRequest;
import com.bapakfadil.blog.responses.comment.CreateCommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    // Mapper for Create Comment
    Comment mapFromCreateCommentRequest(CreateCommentRequest createPostRequest);

    CreateCommentResponse mapToCreateCommentResponse (Comment comment);
}
