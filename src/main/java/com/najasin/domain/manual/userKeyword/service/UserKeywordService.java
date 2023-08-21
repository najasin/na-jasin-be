package com.najasin.domain.manual.userKeyword.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.najasin.domain.manual.dto.param.JffMyKeywordPercent;
import com.najasin.domain.manual.keyword.entity.Keyword;
import com.najasin.domain.manual.userKeyword.entity.UserKeyword;
import com.najasin.domain.manual.userKeyword.repository.UserKeywordRepository;
import com.najasin.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserKeywordService {
	private final UserKeywordRepository userKeywordRepository;

	@Transactional
	public void saveAll(List<JffMyKeywordPercent> keywordPercents, List<Keyword> keywords, User user) {
		keywordPercents.sort(Comparator.comparing(JffMyKeywordPercent::id));
		keywords.sort(Comparator.comparing(Keyword::getId));

		for (int i = 0; i < keywordPercents.size(); i++) {
			save(keywordPercents.get(i).toUserKeywordEntity(user, keywords.get(i)));
		}
	}

	@Transactional
	public UserKeyword save(UserKeyword userKeyword) {
		return userKeywordRepository.save(userKeyword);
	}

	@Transactional(readOnly = true)
	public List<UserKeyword> findByUserId(String userId) {
		return userKeywordRepository.findByUserId(userId);
	}
}
