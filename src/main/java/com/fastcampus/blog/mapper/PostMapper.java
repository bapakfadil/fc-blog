package com.fastcampus.blog.mapper;

import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.requests.posts.CreatePostRequest;
import com.fastcampus.blog.requests.posts.UpdatePostRequest;
import com.fastcampus.blog.responses.posts.CreatePostResponse;
import com.fastcampus.blog.responses.posts.GetPostBySlugResponse;
import com.fastcampus.blog.responses.posts.UpdatePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    // Create Post
    Post mapToCreatePostRequest(CreatePostRequest createPost);
    // @Mapping(source = "slug", target = "path") // jika terdapat perbedaan nama field, maka ini adalah solusinya
    CreatePostResponse mapToCreatePostResponse(Post post);

    // Get Post by Slug
    GetPostBySlugResponse mapToGetPostBySlugResponse(Post post);

    // Update Post
    void mapToUpdatePostRequest(UpdatePostRequest updatedPost, @MappingTarget Post post);
    UpdatePostResponse mapToUpdatePostResponse(Post post);

}
