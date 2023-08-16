package com.najasin.domain.user.dto.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserResponse {
	SUCCESS_LOGOUT("로그아웃에 성공하셨습니다."),
	SUCCESS_UPDATE("업데이트에 성공하였습니다."),
	SUCCESS_GET_PAGE("마이페이지 로딩에 성공하였습니다.");
	private final String message;
}
