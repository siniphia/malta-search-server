package com.malta.keyword.repository;

import com.malta.keyword.entity.Keyword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class KeywordRepositoryTest {
    @Autowired
    KeywordRepository keywordRepository;

    private Keyword keyword;

    @BeforeEach
    public void setUp() {
        this.keyword = Keyword.of("sample-keyword");
        this.keywordRepository.save(this.keyword);
    }

    @Test
    @DisplayName("success - find by existing keyword")
    public void findByKeywordShouldSuccess() {
        // when
        Optional<Keyword> optionalKeyword = this.keywordRepository.findByKeyword("sample-keyword");

        // then
        assertThat(optionalKeyword).isEqualTo(Optional.of(this.keyword));
    }

    @Test
    @DisplayName("success - find by non-existing keyword")
    public void findByNotExistingKeywordShouldSuccess() {
        // when
        Optional<Keyword> optionalKeyword = this.keywordRepository.findByKeyword("non-existing-keyword");

        // then
        assertThat(optionalKeyword).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("success - find top 10 keywords when empty")
    public void findTop10KeywordsWhenEmptyShouldSuccess() {
        // when
        this.keywordRepository.delete(this.keyword);
        List<Keyword> keywords = this.keywordRepository.findTop10ByOrderByCounterDescModifiedTimeDesc();

        // then
        assertThat(keywords)
                .isEmpty();
    }

    @Test
    @DisplayName("success - find top 10 keywords")
    public void findTop10KeywordsShouldSuccess() {
        // when
        List<Keyword> keywords = this.keywordRepository.findTop10ByOrderByCounterDescModifiedTimeDesc();

        // then
        assertThat(keywords)
                .isNotEmpty()
                .contains(this.keyword);
    }
}
