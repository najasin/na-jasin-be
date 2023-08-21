package com.najasin.domain.character.dto.response;

public record CharacterItemsResponse(String baseImage, CharacterItems characterItems) {

	public static CharacterItemsResponse of(String baseImage, CharacterItems characterItems) {
		return new CharacterItemsResponse(baseImage, characterItems);
	}
}
