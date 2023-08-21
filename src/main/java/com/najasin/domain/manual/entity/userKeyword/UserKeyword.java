package com.najasin.domain.manual.entity.userKeyword;

import com.najasin.domain.manual.entity.keyword.Keyword;
import com.najasin.domain.user.dto.param.MyKeywordPercentParam;
import com.najasin.domain.user.entity.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "user_keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@IdClass(UserKeywordId.class)
public class UserKeyword {
	@Id
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "keyword_id", referencedColumnName = "keyword_id", insertable = false, updatable = false)
	private Keyword keyword;

	@Column
	private int originPercent;

	@Column
	private int othersPercent;

	@Column
	private int othersCount;

	public UserKeyword(User user, Keyword keyword, int originPercent) {
		this.user = user;
		this.keyword = keyword;
		this.originPercent = originPercent;
		this.othersPercent = 0;
		this.othersCount = 0;
	}

	public MyKeywordPercentParam toMyKeywordPercentParam() {
		return new MyKeywordPercentParam(keyword.getId(), keyword.getName(), originPercent);
	}
}
