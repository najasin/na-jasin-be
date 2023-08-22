package com.najasin.domain.user.dto.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserTypeMessage {
	SUCCESS_UPDATE_USER_TYPE("유저 타입이 변경됐습니다.");

	private final String msg;
}
