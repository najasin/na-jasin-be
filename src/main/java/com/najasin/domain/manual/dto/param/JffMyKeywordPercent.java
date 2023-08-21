package com.najasin.domain.manual.dto.param;

import com.najasin.domain.manual.keyword.entity.Keyword;
import com.najasin.domain.manual.userKeyword.entity.UserKeyword;
import com.najasin.domain.user.entity.User;

public record JffMyKeywordPercent(Long id,
								  Integer percent) {

	public UserKeyword toUserKeywordEntity(User user, Keyword keyword) {
		return new UserKeyword(user, keyword, percent);
	}
}
