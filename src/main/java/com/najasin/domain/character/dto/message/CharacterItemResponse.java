package com.najasin.domain.character.dto.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CharacterItemResponse {
	FIND_ALL_ITEMS_SUCCESS("모든 아이템 조회에 성공하셨습니다.");

	private final String msg;
}
