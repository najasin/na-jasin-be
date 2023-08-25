package com.najasin.domain.user.dto.param;

import com.najasin.domain.character.dto.param.CharacterItemParam;

import lombok.Builder;

public record MyCharacterItemsParam(CharacterItemParam face,
									CharacterItemParam body,
									CharacterItemParam expression,
									CharacterItemParam set) {
	@Builder
	public MyCharacterItemsParam {
	}
}
