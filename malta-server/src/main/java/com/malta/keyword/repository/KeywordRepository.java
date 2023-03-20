package com.malta.keyword.repository;

import com.malta.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
//    @Query("select * from keyword")
//    List<Keyword> findPopularKeywords(Integer maxRank);
    Optional<Keyword> findByKeyword(String keyword);
}
