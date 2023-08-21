package com.najasin.domain.manual.dto.request;

import java.util.List;

import com.najasin.domain.manual.dto.param.JffAnswerParam;
import com.najasin.domain.manual.dto.param.JffKeywordPercentParam;
import com.najasin.domain.manual.dto.param.ManualCharacterItems;

public record MyManualCreateRequest(String nickname,
									ManualCharacterItems characterItems,
									List<JffAnswerParam> answers,
									List<JffKeywordPercentParam> keywordPercents) {

	public List<Long> getQuestionIdList() {
		return answers.stream().map(JffAnswerParam::id).toList();
	}

	public List<Long> getKeywordIdList() {
		return keywordPercents.stream().map(JffKeywordPercentParam::id).toList();
	}
}
