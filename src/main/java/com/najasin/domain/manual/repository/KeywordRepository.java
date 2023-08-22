package com.najasin.domain.manual.repository;

import com.najasin.domain.manual.entity.keyword.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findKeywordByName(String name);
}
