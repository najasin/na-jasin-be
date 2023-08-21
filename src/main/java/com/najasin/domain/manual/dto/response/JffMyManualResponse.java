package com.najasin.domain.manual.dto.response;

import java.util.List;

import com.najasin.domain.character.dto.param.CharacterItemsParam;
import com.najasin.domain.manual.dto.param.JffKeywordParam;
import com.najasin.domain.manual.dto.param.JffQuestionParam;

public record JffMyManualResponse(String baseImage,
								  CharacterItemsParam characterItems,
								  List<JffQuestionParam> questions,
								  List<JffKeywordParam> exampleKeywords) {

	public static JffMyManualResponse of(
		String baseImage,
		CharacterItemsParam characterItems,
		List<JffQuestionParam> questions,
		List<JffKeywordParam> exampleKeywords) {
		return new JffMyManualResponse(baseImage, characterItems, questions, exampleKeywords);
	}
}
