package com.najasin.domain.manual.dto.response;

public record JffMyManualCreateResponse(String userId,
										String userType) {

	public static JffMyManualCreateResponse of(String userId, String userType) {
		return new JffMyManualCreateResponse(userId, userType);
	}
}
