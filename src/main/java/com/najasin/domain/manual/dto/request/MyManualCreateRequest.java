package com.najasin.domain.manual.dto.request;

import java.util.List;

import com.najasin.domain.manual.dto.param.JffMyAnswer;
import com.najasin.domain.manual.dto.param.JffMyKeywordPercent;
import com.najasin.domain.manual.dto.param.ManualCharacterItems;

public record MyManualCreateRequest(String nickname,
									ManualCharacterItems characterItems,
									List<JffMyAnswer> answers,
									List<JffMyKeywordPercent> keywordPercents) {

	public List<Long> getQuestionIdList() {
		return answers.stream().map(JffMyAnswer::id).toList();
	}

	public List<Long> getKeywordIdList() {
		return keywordPercents.stream().map(JffMyKeywordPercent::id).toList();
	}
}
