package com.najasin.domain.character.dto.param;

import java.util.List;

import lombok.Builder;

public record CharacterItemsParam(List<CharacterItemParam> face,
								  List<CharacterItemParam> body,
								  List<CharacterItemParam> expression,
								  List<CharacterItemParam> set) {
	@Builder
	public CharacterItemsParam {}
}
