package com.najasin.domain.manual.repository;

import java.util.List;

import com.najasin.domain.manual.entity.userKeyword.UserKeyword;
import com.najasin.domain.manual.entity.userKeyword.UserKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, UserKeywordId> {
	List<UserKeyword> findByUserId(String userId);
}
