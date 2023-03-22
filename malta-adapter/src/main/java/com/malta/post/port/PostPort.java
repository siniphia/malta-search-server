package com.malta.post.port;

import com.malta.post.dto.PostResponseDto;
import com.malta.post.util.Pagination;
import jakarta.validation.constraints.NotNull;

public interface PostPort {
    Pagination<PostResponseDto> searchPostsByKeyword(
            @NotNull String keyword,
            String sort,
            Integer page,
            Integer size
    );
}
