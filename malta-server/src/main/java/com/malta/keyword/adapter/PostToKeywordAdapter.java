package com.malta.keyword.adapter;

import com.malta.post.dto.PostResponseDto;
import com.malta.post.port.PostPort;
import com.malta.post.web.PostController;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostToKeywordAdapter implements PostPort {
    @Autowired
    private final PostController postController;

    @Override
    public List<PostResponseDto> searchPostsByKeyword(
            @NotNull String keyword,
            String sort,
            Integer page,
            Integer size
    ) {
        return postController.getBlogPostsFromKakao(keyword, sort, page, size)
                .collectList()
                .block();
    }
}
