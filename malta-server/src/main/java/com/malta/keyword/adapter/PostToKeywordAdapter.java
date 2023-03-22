package com.malta.keyword.adapter;

import com.malta.post.dto.PostResponseDto;
import com.malta.post.port.PostPort;
import com.malta.post.util.Pagination;
import com.malta.post.web.PostController;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostToKeywordAdapter implements PostPort {
    @Autowired
    private final PostController postController;

    @Override
    public Pagination<PostResponseDto> searchPostsByKeyword(
            @NotNull String keyword,
            String sort,
            Integer page,
            Integer size
    ) {
        return postController.getBlogPostsFromKakao(keyword, sort, page, size);
    }
}
