package com.bapakfadil.blog.mapper;

import com.bapakfadil.blog.entities.Post;
import com.bapakfadil.blog.requests.CreatePostRequest;
import com.bapakfadil.blog.responses.CreatePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    // Mapper for Create Request
    Post map (CreatePostRequest createPostRequest);
    CreatePostResponse map (Post post);
}
