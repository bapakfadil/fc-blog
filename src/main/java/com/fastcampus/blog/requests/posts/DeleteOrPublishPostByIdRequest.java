package com.fastcampus.blog.requests.posts;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteOrPublishPostByIdRequest {
    @Positive
    @NotNull
    private Integer id;
}
