package com.najasin.domain.user.dto.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserResponse {
	SUCCESS_LOGOUT("로그아웃에 성공하셨습니다."),
	SUCCESS_UPDATE_NICKNAME("닉네임 수정에 성공하였습니다."),
	SUCCESS_UPDATE_CHARACTER("캐릭터 수정에 성공하였습니다."),
	SUCCESS_UPDATE_ANSWER("답변 수정에 성공하였습니다."),
	SUCCESS_GET_MY_PAGE("마이 페이지 조회에 성공하였습니다."),
	SUCCESS_RECREATE_ACCESS_TOKEN("액세스 토큰 재발급에 성공하셨습니다.");
	private final String message;
}
