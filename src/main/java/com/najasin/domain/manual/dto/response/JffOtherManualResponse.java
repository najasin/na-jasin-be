package com.najasin.domain.manual.dto.response;

import java.util.List;

import com.najasin.domain.manual.dto.param.JffQuestionParam;
import com.najasin.domain.user.dto.param.AnswerParam;
import com.najasin.domain.user.dto.param.CommentParam;
import com.najasin.domain.user.dto.param.MyCharacterItemsParam;
import com.najasin.domain.user.dto.param.KeywordPercentParam;

import lombok.Builder;

public record JffOtherManualResponse(String nickname,
									 String baseImage,
									 MyCharacterItemsParam characterItems,
									 List<JffQuestionParam> questions,
									 List<AnswerParam> myManualQAPair,
									 List<CommentParam> othersManualQAPairs,
									 List<KeywordPercentParam> originKeywordPercents,
									 List<KeywordPercentParam> otherKeywordPercents) {

	@Builder
	public JffOtherManualResponse {
	}
}
