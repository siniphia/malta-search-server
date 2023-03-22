package com.malta.post.service;

import com.malta.post.dto.PostResponseDto;
import com.malta.post.util.Pagination;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @InjectMocks
    private PostService postService;

    private String kakaoResponse;
    private String naverResponse;
    private PostResponseDto firstDto, secondDto;
    private Pagination<PostResponseDto> pageDto;
    private Integer page, size, total;

    @BeforeEach
    public void setUp() {
        this.kakaoResponse =
                """
                        {
                            "meta" :
                                {
                                    "pageable_count": 100 
                                },
                            "documents": [
                                {
                                    "title": "테스트 게시글 1",
                                    "blogname": "테스트 블로그 1",
                                    "url": "http://blog.sample.com/1",
                                    "contents": "첫번째 테스트용 데이터입니다.",
                                    "datetime": "2023-02-19T20:54:31.000+09:00"
                                },
                                {
                                    "title": "테스트 게시글 2",
                                    "blogname": "테스트 블로그 2",
                                    "url": "http://blog.sample.com/2",
                                    "contents": "두번째 테스트용 데이터입니다.",
                                    "datetime": "2023-02-18T23:55:23.000+09:00"
                                }
                            ]
                        }
                """;

        this.naverResponse =
                """
                        {
                            "total": 100,
                            "items": [
                                {
                                    "title": "테스트 게시글 1",
                                    "bloggername": "테스트 블로그 1",
                                    "link": "http://blog.sample.com/1",
                                    "description": "첫번째 테스트용 데이터입니다.",
                                    "postdate": "2023-02-19T20:54:31.000+09:00"
                                },
                                {
                                    "title": "테스트 게시글 2",
                                    "bloggername": "테스트 블로그 2",
                                    "link": "http://blog.sample.com/2",
                                    "description": "두번째 테스트용 데이터입니다.",
                                    "postdate": "2023-02-18T23:55:23.000+09:00"
                                }
                            ]
                        }
                """;

        this.firstDto = PostResponseDto.from(
                "테스트 게시글 1",
                "테스트 블로그 1",
                "http://blog.sample.com/1",
                "첫번째 테스트용 데이터입니다.",
                "2023-02-19T20:54:31.000+09:00"
        );

        this.secondDto = PostResponseDto.from(
                "테스트 게시글 2",
                "테스트 블로그 2",
                "http://blog.sample.com/2",
                "두번째 테스트용 데이터입니다.",
                "2023-02-18T23:55:23.000+09:00"
        );

        this.page = 1;
        this.size = 10;
        this.total = 100;
        this.pageDto = new Pagination<>(this.page, this.size, this.total, List.of(this.firstDto, this.secondDto));
    }

    @Test
    @DisplayName("success - getting kakao blog posts : normal case")
    public void gettingKakaoBlogPostsShouldSuccess() {
        // when
        Pagination<PostResponseDto> dtos = this.postService.getKakaoBlogPosts(this.kakaoResponse, this.page, this.size);

        // then
        assertThat(dtos)
                .extracting("nextPageNum", "prevPageNum", "results")
                .contains(this.pageDto.getNextPageNum(), this.pageDto.getPrevPageNum(), this.pageDto.getResults());
    }

    @Test
    @DisplayName("fail - getting kakao blog posts : wrong json format")
    public void gettingKakaoBlogPostsWithInvalidJsonFormatShouldFail() {
        // given
        String invalidResponse = "invalid-json";

        // when
        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> this.postService.getKakaoBlogPosts(invalidResponse, this.page, this.size)
        );

        // then
        assertThat(exception.getClass()).isEqualTo(JSONException.class);
    }

    @Test
    @DisplayName("success - getting naver blog posts")
    public void gettingNaverBlogPostsShouldSuccess() {
        // when
        Pagination<PostResponseDto> dtos = this.postService.getNaverBlogPosts(this.naverResponse, this.page, this.size);

        // then
        assertThat(dtos)
                .extracting("nextPageNum", "prevPageNum", "results")
                .contains(this.pageDto.getNextPageNum(), this.pageDto.getPrevPageNum(), this.pageDto.getResults());
    }

    @Test
    @DisplayName("fail - getting naver blog posts : wrong json format")
    public void gettingNaverBlogPostsWithInvalidJsonFormatShouldFail() {
        // given
        String invalidResponse = "invalid-json";

        // when
        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> this.postService.getNaverBlogPosts(invalidResponse, this.page, this.size)
        );

        // then
        assertThat(exception.getClass()).isEqualTo(JSONException.class);
    }
}
