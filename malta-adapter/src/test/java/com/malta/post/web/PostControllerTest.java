package com.malta.post.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malta.post.dto.PostResponseDto;
import com.malta.post.service.PostService;
import com.malta.post.util.Pagination;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import org.assertj.core.api.Assertions;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    private PostResponseDto firstDto, secondDto;
    private Pagination<PostResponseDto> pageDto;
    private Integer page, size, total;
    private String keyword;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

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
        this.keyword = "test-keyword";
        this.pageDto = new Pagination<>(this.page, this.size, this.total, List.of(this.firstDto, this.secondDto));
    }

    @Test
    @DisplayName("success - getting blog posts from kakao with keyword")
    public void gettingBlogPostsFromKakaoWithKeywordShouldSuccess() throws Exception {
        // given
        given(this.postService.getKakaoBlogPosts(anyString(), anyInt(), anyInt())).willReturn(this.pageDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                get("/api/v1/post/kakao")
                        .queryParam("keyword", keyword)
                        .accept(MediaType.ALL)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string(this.objectMapper.writeValueAsString(this.pageDto)))
                .andDo(print());
    }

    @Test
    @DisplayName("success - getting blog posts from kakao with all parameters")
    public void gettingBlogPostsFromKakaoWithAllParametersShouldSuccess() throws Exception {
        // given
        given(this.postService.getKakaoBlogPosts(anyString(), anyInt(), anyInt())).willReturn(this.pageDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                get("/api/v1/post/kakao")
                        .queryParam("keyword", keyword)
                        .queryParam("sort", "accuracy")
                        .queryParam("page", "1")
                        .queryParam("size", "10")
                        .accept(MediaType.ALL)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string(this.objectMapper.writeValueAsString(this.pageDto)))
                .andDo(print());
    }

    @Test
    @DisplayName("fail - getting blog posts from kakao with invalid sort")
    public void gettingBlogPostsFromKakaoWithInvalidSortShouldFail() throws Exception {
        // given
        String invalidSort = "invalid-sort";
        given(this.postService.getKakaoBlogPosts(anyString(), anyInt(), anyInt())).willReturn(this.pageDto);

        // when
        Assertions.assertThatThrownBy(() -> this.mockMvc.perform(
                get("/api/v1/post/kakao")
                        .queryParam("keyword", keyword)
                        .queryParam("sort", invalidSort)
                        .accept(MediaType.ALL)
                )
        ).hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("fail - getting blog posts from kakao with invalid page and size")
    public void gettingBlogPostsFromKakaoWithInvalidPageAndSizeShouldFail() {
        // given
        int invalidPage = 300;
        int invalidSize = 300;
        given(this.postService.getKakaoBlogPosts(anyString(), anyInt(), anyInt())).willReturn(this.pageDto);

        // when
        Assertions.assertThatThrownBy(() -> this.mockMvc.perform(
                get("/api/v1/post/kakao")
                        .queryParam("keyword", keyword)
                        .queryParam("page", Integer.toString(invalidPage))
                        .queryParam("size", Integer.toString(invalidSize))
                        .accept(MediaType.ALL)
                )
        ).hasCauseInstanceOf(ConstraintViolationException.class);
    }
}
