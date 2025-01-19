package com.fastcampus.blog.response;

import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPostResponse {
    private Integer id;
    private String title;
    private String body;
    private String slug;
    private Category category;
    private Long publishedAt;
    private Long commentCount;
    private boolean published;
}
