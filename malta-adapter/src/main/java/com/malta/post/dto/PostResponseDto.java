package com.malta.post.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponseDto {
    private final String title;
    private final String blogName;
    private final String url;
    private final String contents;
    private final String postDate;

    public static PostResponseDto from(
            String title,
            String blogName,
            String url,
            String contents,
            String postDate
    ) {
        return new PostResponseDto(title, blogName, url, contents, postDate);
    }
}
