package com.fastcampus.blog.mapper;

import com.fastcampus.blog.entities.Post;
import com.fastcampus.blog.requests.posts.CreatePostRequest;
import com.fastcampus.blog.requests.posts.UpdatePostRequest;
import com.fastcampus.blog.responses.posts.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List; // Import List

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    // Create Post
    Post mapToCreatePostRequest(CreatePostRequest createPost);
    CreatePostResponse mapToCreatePostResponse(Post post);

    // Get Post by Slug
    GetPostBySlugResponse mapToGetPostBySlugResponse(Post post);

    // Update Post
    void mapToUpdatePostRequest(UpdatePostRequest updatedPost, @MappingTarget Post post);
    UpdatePostResponse mapToUpdatePostResponse(Post post);

    // Publish Post
    @Mapping(source = "isPublished", target = "published")
    PublishPostResponse mapToPublishPost (Post post);

    // Delete Post
    DeletePostResponse mapToDeletePostResponse(Post post);

    // Get Posts
    @Mapping(source = "isPublished", target = "published")
    GetPostsResponse mapToGetPostsResponse(Post post);
    List<GetPostsResponse> mapToGetPostsResponseList(List<Post> posts); // Tambahkan ini
}
