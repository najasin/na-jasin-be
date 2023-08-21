package com.najasin.domain.myManual.keyword.repository;

import com.najasin.domain.myManual.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findKeywordByName(String name);
}
