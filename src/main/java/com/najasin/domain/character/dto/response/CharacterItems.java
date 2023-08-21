package com.najasin.domain.character.dto.response;

import java.util.List;

import lombok.Builder;

public record CharacterItems(List<CharacterItem> face,
							 List<CharacterItem> body,
							 List<CharacterItem> expression,
							 List<CharacterItem> set) {
	@Builder
	public CharacterItems {}
}
