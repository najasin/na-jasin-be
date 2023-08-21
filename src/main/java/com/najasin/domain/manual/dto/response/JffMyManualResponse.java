package com.najasin.domain.manual.dto.response;

import java.util.List;

import com.najasin.domain.character.dto.response.CharacterItems;
import com.najasin.domain.manual.dto.param.JffMyKeyword;
import com.najasin.domain.manual.dto.param.JffMyQuestion;

public record JffMyManualResponse(String baseImage,
								  CharacterItems characterItems,
								  List<JffMyQuestion> questions,
								  List<JffMyKeyword> exampleKeywords) {

	public static JffMyManualResponse of(
		String baseImage,
		CharacterItems characterItems,
		List<JffMyQuestion> questions,
		List<JffMyKeyword> exampleKeywords) {
		return new JffMyManualResponse(baseImage, characterItems, questions, exampleKeywords);
	}
}
