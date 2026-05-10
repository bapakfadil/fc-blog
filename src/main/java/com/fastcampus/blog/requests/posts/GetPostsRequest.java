package com.fastcampus.blog.requests.posts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPostsRequest {
    private Integer pageNo;
    private Integer limit;
}
