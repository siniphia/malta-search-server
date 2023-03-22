package com.malta.post.web;

import com.malta.post.dto.PostResponseDto;
import com.malta.post.service.PostService;
import com.malta.post.util.Pagination;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    @Value("${auth.kakao.access-key}")
    private String kakaoKey;

    @Value("${auth.naver.access-id}")
    private String naverId;

    @Value("${auth.naver.access-key}")
    private String naverKey;

    @GetMapping("/kakao")
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name = "postServer", fallbackMethod = "getBlogPostsFromNaver")
    public Pagination<PostResponseDto> getBlogPostsFromKakao(
            @RequestParam String keyword,
            @RequestParam(required = false, defaultValue = "accuracy") String sort,
            @Min(1) @Max(50) @RequestParam(required = false, defaultValue = "1") Integer page,
            @Min(1) @Max(50) @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        if (!sort.equals("accuracy") && !sort.equals("recency")) {
            throw new IllegalArgumentException("Expected : accuracy / recency,\nActual : " + sort);
        }

        WebClient webClient = WebClient.builder()
                .baseUrl("https://dapi.kakao.com/v2")
                .build();

        String httpResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search/blog")
                        .queryParam("query", keyword)
                        .queryParam("sort", sort)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .header("Authorization", "KakaoAK " + "da50203af9034dc757b85cb69996d611")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return this.postService.getKakaoBlogPosts(httpResponse, page, size);
    }

    @GetMapping("/naver")
    @ResponseStatus(HttpStatus.OK)
    public Pagination<PostResponseDto> getBlogPostsFromNaver(
            @RequestParam String keyword,
            @RequestParam(required = false, defaultValue = "accuracy") String sort,
            @Min(1) @Max(100) @RequestParam(required = false, defaultValue = "1") Integer page,
            @Min(1) @Max(100) @RequestParam(required = false, defaultValue = "10") Integer size,
            CallNotPermittedException callNotPermittedException
    ) {
        if (!sort.equals("accuracy") && !sort.equals("recency")) {
            throw new IllegalArgumentException("Expected : accuracy / recency,\nActual : " + sort);
        }

        WebClient webClient = WebClient.builder()
                .baseUrl("https://openapi.naver.com/v1")
                .build();

        String httpResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search/blog.json")
                        .queryParam("query", keyword)
                        .queryParam("sort", sort.equals("accuracy") ? "sim" : "date")
                        .queryParam("start", page)
                        .queryParam("display", size)
                        .build())
                .header("X-Naver-Client-Id", naverId)
                .header("X-Naver-Client-Secret", naverKey)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return this.postService.getNaverBlogPosts(httpResponse, page, size);
    }
}
