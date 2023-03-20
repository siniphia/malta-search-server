package com.malta.post.port;

import com.malta.post.dto.PostResponseDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface PostPort {
    List<PostResponseDto> searchPostsByKeyword(
            @NotNull String keyword,
            String sort,
            Integer page,
            Integer size);
}
