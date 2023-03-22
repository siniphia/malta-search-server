package com.malta.post.util;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T> {
    private Integer nextPageNum;
    private Integer prevPageNum;
    private Integer totalSize;
    private Integer pageSize;
    private Boolean isEnd;
    private List<T> results;

    public Pagination(
            Integer pageNum,
            Integer pageSize,
            Integer totalSize,
            List<T> results
    ) {
        Integer maxPageNum = totalSize / pageSize;

        this.nextPageNum = pageNum.equals(maxPageNum) ? null : pageNum + 1;
        this.prevPageNum = pageNum.equals(1) ? null : pageNum - 1;
        this.totalSize = totalSize;
        this.pageSize = pageSize;
        this.isEnd = nextPageNum == null;
        this.results = results;
    }
}
