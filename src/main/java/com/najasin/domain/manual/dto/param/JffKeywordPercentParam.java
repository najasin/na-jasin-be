package com.najasin.domain.manual.dto.param;

import com.najasin.domain.manual.entity.keyword.Keyword;
import com.najasin.domain.manual.entity.userKeyword.UserKeyword;
import com.najasin.domain.user.entity.User;

public record JffKeywordPercentParam(Long id,
									 Integer percent) {

	public UserKeyword toUserKeywordEntity(User user, Keyword keyword) {
		return new UserKeyword(user, keyword, percent);
	}
}
