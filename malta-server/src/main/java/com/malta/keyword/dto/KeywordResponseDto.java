package com.malta.keyword.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KeywordResponseDto {
    private final Integer order;
    private final String keyword;
    private final Integer count;

    public static KeywordResponseDto from(
            Integer order,
            String keyword,
            Integer count
    ) {
        return new KeywordResponseDto(order, keyword, count);
    }
}
