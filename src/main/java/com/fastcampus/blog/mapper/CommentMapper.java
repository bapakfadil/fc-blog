package com.fastcampus.blog.mapper;

import com.fastcampus.blog.entity.Comment;
import com.fastcampus.blog.request.CreateCommentRequest;
import com.fastcampus.blog.response.CreateCommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    Comment mapFromCreateCommentRequest(CreateCommentRequest createCommentRequest);
    CreateCommentResponse mapToCreateCommentResponse(Comment comment);
}
