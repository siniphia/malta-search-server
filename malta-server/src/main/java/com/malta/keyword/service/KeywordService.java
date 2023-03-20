package com.malta.keyword.service;

import com.malta.keyword.dto.KeywordResponseDto;
import com.malta.keyword.entity.Keyword;
import com.malta.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    @Transactional
    public void searchBlogPosts(String keyword) {
        this.keywordRepository.findByKeyword(keyword)
                .ifPresentOrElse(
                        e -> this.keywordRepository.save(e.increaseCounter()),
                        () -> this.keywordRepository.save(Keyword.of(keyword))
                );
    }

    @Transactional(readOnly = true)
    public List<KeywordResponseDto> getPopularKeywords() {
//        List<Keyword> entities = this.keywordRepository.findPopularKeywords(10);
//
//        return IntStream.range(0, entities.size())
//                .mapToObj(i -> KeywordResponseDto.from(i, entities.get(i).getKeyword(), entities.get(i).getCounter()))
//                .collect(Collectors.toList());
        return List.of(KeywordResponseDto.from(1, "hi", 1));
    }
}
