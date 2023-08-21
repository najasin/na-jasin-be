package com.najasin.domain.manual.userKeyword.repository;

import com.najasin.domain.manual.userKeyword.entity.UserKeyword;
import com.najasin.domain.manual.userKeyword.entity.UserKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, UserKeywordId> {
}
