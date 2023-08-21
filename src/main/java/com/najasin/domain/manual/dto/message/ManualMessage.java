package com.najasin.domain.manual.dto.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ManualMessage {
	FIND_MY_MANUAL_SUCCESS("my manual 조회에 성공했습니다."),
	CREATE_MY_MANUAL_SUCCESS("my manual 생성에 성공했습니다.");
	private final String msg;
}
