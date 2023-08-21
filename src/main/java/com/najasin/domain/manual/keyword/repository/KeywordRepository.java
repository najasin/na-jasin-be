package com.najasin.domain.manual.keyword.repository;

import com.najasin.domain.manual.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findKeywordByName(String name);
}
