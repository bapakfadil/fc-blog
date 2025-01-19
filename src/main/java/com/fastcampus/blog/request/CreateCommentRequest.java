package com.fastcampus.blog.request;

import com.fastcampus.blog.entity.Post;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class CreateCommentRequest {
    @Size(min = 2, max = 100, message = "Minimal 2 dan maksimal 100 karakter")
    @NotNull(message = "Nama tidak boleh kosong")
    private String name;

    @Size(min = 2, max = 100)
    @Email(message = "Masukkan email yang valid")
    @NotNull(message = "Email tidak boleh kosong")
    private String email;

    @Size(min = 2, max = 10_000, message = "Minimal 2 dan maksimal 10.000 karakter")
    @NotNull(message = "Isi komentar tidak boleh kosong")
    private String body;

    @NotNull(message = "Post slug tidak boleh kosong")
    private Post post;
}
