package com.malta.keyword.web;

import com.malta.keyword.adapter.PostToKeywordAdapter;
import com.malta.keyword.dto.KeywordResponseDto;
import com.malta.keyword.service.KeywordService;
import com.malta.post.dto.PostResponseDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/keyword")
public class KeywordController {
    private final KeywordService keywordService;
    private final PostToKeywordAdapter postToKeywordAdapter;

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseDto> searchBlogPosts(
            @RequestParam String keyword,
            @RequestParam(required = false, defaultValue = "accuracy") String sort,
            @Min(1) @Max(50) @RequestParam(required = false, defaultValue = "1") Integer page,
            @Min(1) @Max(50) @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        this.keywordService.searchBlogPosts(keyword);
        return postToKeywordAdapter.searchPostsByKeyword(keyword, sort, page, size);
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<KeywordResponseDto> getPopularKeywords() {
        return this.keywordService.getPopularKeywords();
    }
}
