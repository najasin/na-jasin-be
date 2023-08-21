package com.najasin.domain.manual.entity.userKeyword;

import com.najasin.domain.manual.entity.keyword.Keyword;
import com.najasin.domain.user.dto.param.KeywordPercentParam;
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
	private Integer originPercent;

	@Column
	private Integer othersPercent;

	@Column
	private Integer othersCount;

	public UserKeyword(User user, Keyword keyword, int originPercent) {
		this.user = user;
		this.keyword = keyword;
		this.originPercent = originPercent;
		this.othersPercent = 0;
		this.othersCount = 1;
	}

	public void updateOthersPercent(int othersPercent) {
		this.othersCount++;

		if(this.othersCount == 2) {
			this.othersPercent += this.originPercent;
		}

		this.othersPercent = (this.othersPercent +othersPercent) / this.othersCount;
	}

	public Long getKeyWordId() {
		return keyword.getId();
	}

	public KeywordPercentParam toMyKeywordPercentParam() {
		return new KeywordPercentParam(keyword.getId(), keyword.getName(), originPercent);
	}

	public KeywordPercentParam toOthersKeywordPercentParam() {
		return new KeywordPercentParam(keyword.getId(), keyword.getName(), othersCount);
	}
}
