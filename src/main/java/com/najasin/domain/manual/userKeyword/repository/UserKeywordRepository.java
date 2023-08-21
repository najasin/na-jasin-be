package com.najasin.domain.userKeyword.repository;

import com.najasin.domain.userKeyword.entity.UserKeyword;
import com.najasin.domain.userKeyword.entity.UserKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, UserKeywordId> {
}
