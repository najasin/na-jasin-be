package com.najasin.domain.keyword.repository;

import com.najasin.domain.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findKeywordByName(String name);
}
