package com.malta.keyword.service;

import com.malta.keyword.dto.KeywordResponseDto;
import com.malta.keyword.entity.Keyword;
import com.malta.keyword.repository.KeywordRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class KeywordServiceTest {
    @InjectMocks
    private KeywordService keywordService;

    @Mock
    private KeywordRepository keywordRepository;

    private Keyword keywordEntity1, keywordEntity2;
    private KeywordResponseDto keywordDto1, keywordDto2;

    private MockedStatic<KeywordResponseDto> keywordResponseDtoMockedStatic;

    @BeforeEach
    public void setUp() {
        this.keywordEntity1 = Keyword.of("top-1");
        this.keywordEntity2 = Keyword.of("top-2");

        this.keywordDto1 = KeywordResponseDto.from(this.keywordEntity1);
        this.keywordDto2 = KeywordResponseDto.from(this.keywordEntity2);

        this.keywordResponseDtoMockedStatic = mockStatic(KeywordResponseDto.class);
    }

    @AfterEach
    public void close() {
        this.keywordResponseDtoMockedStatic.close();
    }

    @Test
    @DisplayName("success - get empty popular keywords")
    public void getEmptyPopularKeywordsShouldSuccess() {
        // given
        given(this.keywordRepository.findTop10ByOrderByCounterDescModifiedTimeDesc())
                .willReturn(List.of());

        // when
        List<KeywordResponseDto> dtos = this.keywordService.getPopularKeywords();

        // then
        assertThat(dtos)
                .isEmpty();
    }

    @Test
    @DisplayName("success - get popular keywords")
    public void getPopularKeywordsShouldSuccess() {
        // given
        given(this.keywordRepository.findTop10ByOrderByCounterDescModifiedTimeDesc())
                .willReturn(List.of(this.keywordEntity1, this.keywordEntity2));

        this.keywordResponseDtoMockedStatic
                .when(() -> KeywordResponseDto.from(this.keywordEntity1))
                .thenReturn(this.keywordDto1);

        this.keywordResponseDtoMockedStatic
                .when(() -> KeywordResponseDto.from(this.keywordEntity2))
                .thenReturn(this.keywordDto2);

        // when
        List<KeywordResponseDto> dtos = this.keywordService.getPopularKeywords();

        // then
        assertThat(dtos)
                .isNotEmpty()
                .extracting(KeywordResponseDto::getKeyword, KeywordResponseDto::getCounter)
                .contains(
                        tuple(this.keywordEntity1.getKeyword(), this.keywordEntity1.getCounter()),
                        tuple(this.keywordEntity2.getKeyword(), this.keywordEntity2.getCounter())
                );
    }
}
