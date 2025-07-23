package com.bapakfadil.blog.mapper;

import com.bapakfadil.blog.entities.Post;
import com.bapakfadil.blog.requests.post.CreatePostRequest;
import com.bapakfadil.blog.responses.post.CreatePostResponse;
import com.bapakfadil.blog.responses.post.GetPostResponse;
import com.bapakfadil.blog.responses.post.UpdatePostBySlugResponse;
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

    // Mapper for Updating Post
    UpdatePostBySlugResponse mapToUpdatePostBySlugResponse(Post post);
}
