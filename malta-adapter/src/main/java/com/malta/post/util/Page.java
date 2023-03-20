package com.malta.post.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {
    private Integer page;
    private Integer size;
    private Long counter;
    private List<T> results;
}
