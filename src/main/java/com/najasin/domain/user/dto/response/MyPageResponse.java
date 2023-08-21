package com.najasin.domain.user.dto.response;

import java.util.List;

import com.najasin.domain.user.dto.param.AnswerParam;
import com.najasin.domain.user.dto.param.CommentParam;
import com.najasin.domain.user.dto.param.MyCharacterItemsParam;
import com.najasin.domain.user.dto.param.KeywordPercentParam;

import lombok.Builder;

public record MyPageResponse(List<String> userTypes,
							 String nickname,
							 String baseImage,
							 MyCharacterItemsParam characterItems,
							 List<AnswerParam> myManualQAPair,
							 List<CommentParam> othersManualQAPairs,
							 List<KeywordPercentParam> originKeywordPercents,
							 List<KeywordPercentParam> otherKeywordPercents,
							 Boolean isOwner) {

	@Builder
	public MyPageResponse {
	}
}
