package com.fastcampus.blog.mapper;

import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.requests.CreatePostRequest;
import com.fastcampus.blog.responses.CreatePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    // Create Post
    Post createPostRequestMap(CreatePostRequest createPost);
    // @Mapping(source = "slug", target = "path") // jika terdapat perbedaan nama field maka ini adalah solusinya
    CreatePostResponse createPostResponseMap(Post post);
}
