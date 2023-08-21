package com.najasin.domain.character.dto.response;

import com.najasin.domain.character.dto.param.CharacterItemsParam;

public record CharacterItemsResponse(String baseImage, CharacterItemsParam characterItems) {

	public static CharacterItemsResponse of(String baseImage, CharacterItemsParam characterItems) {
		return new CharacterItemsResponse(baseImage, characterItems);
	}
}
