package com.malta.keyword.dto;

import com.malta.keyword.entity.Keyword;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KeywordResponseDto {
    private final String keyword;
    private final Integer counter;

    public static KeywordResponseDto of(
            String keyword,
            Integer counter
    ) {
        return new KeywordResponseDto(keyword, counter);
    }

    public static KeywordResponseDto from(Keyword e) {
        return new KeywordResponseDto(
                e.getKeyword(),
                e.getCounter()
        );
    }
}
