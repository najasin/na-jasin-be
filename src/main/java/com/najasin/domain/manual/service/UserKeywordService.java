package com.najasin.domain.manual.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.najasin.domain.manual.dto.param.JffKeywordPercentParam;
import com.najasin.domain.manual.entity.keyword.Keyword;
import com.najasin.domain.manual.entity.userKeyword.UserKeyword;
import com.najasin.domain.manual.repository.UserKeywordRepository;
import com.najasin.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserKeywordService {
	private final UserKeywordRepository userKeywordRepository;

	@Transactional
	public void saveAll(List<JffKeywordPercentParam> keywordPercents, List<Keyword> keywords, User user) {
		keywordPercents.sort(Comparator.comparing(JffKeywordPercentParam::id));
		keywords.sort(Comparator.comparing(Keyword::getId));

		for (int i = 0; i < keywordPercents.size(); i++) {
			save(keywordPercents.get(i).toUserKeywordEntity(user, keywords.get(i)));
		}
	}

	@Transactional
	public UserKeyword save(UserKeyword userKeyword) {
		return userKeywordRepository.save(userKeyword);
	}

	@Transactional
	public void updateAllOthersPercent(
		List<JffKeywordPercentParam> otherKeywordPercents,
		List<UserKeyword> userKeywords) {
		for(int i=0 ; i<userKeywords.size() ; i++) {
			updateOthersPercent(userKeywords.get(i), otherKeywordPercents.get(i).percent());
		}
	}

	@Transactional
	public void updateOthersPercent(UserKeyword userKeyword, int percent) {
		userKeyword.updateOthersPercent(percent);
	}

	@Transactional(readOnly = true)
	public List<UserKeyword> findByUserId(String userId) {
		return userKeywordRepository.findByUserId(userId);
	}
}
