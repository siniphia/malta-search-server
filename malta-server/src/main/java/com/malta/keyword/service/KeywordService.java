package com.malta.keyword.service;

import com.malta.keyword.dto.KeywordResponseDto;
import com.malta.keyword.entity.Keyword;
import com.malta.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    @Transactional
    public void saveOrUpdateKeyword(String keyword) {
        this.keywordRepository.findByKeyword(keyword)
                .ifPresentOrElse(
                        e -> this.keywordRepository.save(e.increaseCounter()),
                        () -> this.keywordRepository.save(Keyword.of(keyword))
                );
    }

    @Transactional(readOnly = true)
    public List<KeywordResponseDto> getPopularKeywords() {
        return keywordRepository.findTop10ByOrderByCounterDesc()
                .stream()
                .map(KeywordResponseDto::from)
                .toList();
    }
}
