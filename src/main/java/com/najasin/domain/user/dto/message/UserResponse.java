package com.najasin.domain.user.dto.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserResponse {
	SUCCESS_LOGOUT("로그아웃에 성공하셨습니다.");

	private final String message;
}
