package com.fastcampus.blog.mapper;

import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.request.CreatePostRequest;
import com.fastcampus.blog.response.CreatePostResponse;
import com.fastcampus.blog.response.GetPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    Post map(CreatePostRequest createPostRequest);
    CreatePostResponse mapToCreatePostResponse(Post post);

    GetPostResponse mapToGetPostResponse(Post post);
}
