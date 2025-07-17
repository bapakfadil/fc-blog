package com.bapakfadil.blog.mapper;

import com.bapakfadil.blog.entities.Post;
import com.bapakfadil.blog.requests.CreatePostRequest;
import com.bapakfadil.blog.responses.CreatePostResponse;
import com.bapakfadil.blog.responses.GetPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    // Mapper for Create Post Request
    Post mapToCreatePostResponse (CreatePostRequest createPostRequest);

    // Mapper for Create Post Response
    CreatePostResponse mapToCreatePostResponse (Post post);

    // Mapper for Get Post
    GetPostResponse mapToGetPostResponse (Post post);
}
