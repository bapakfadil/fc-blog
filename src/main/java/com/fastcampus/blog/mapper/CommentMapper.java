package com.fastcampus.blog.mapper;

import com.fastcampus.blog.entities.Comment;
import com.fastcampus.blog.requests.comments.CreateCommentRequest;
import com.fastcampus.blog.responses.comments.CreateCommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    // Create Comment
    Comment mapToCreateCommentRequest(CreateCommentRequest createCommentRequest);
    CreateCommentResponse mapToCreateCommentResponse(Comment comment);

}
