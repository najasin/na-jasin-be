package com.najasin.domain.user.dto.response;

public record UserTypeResponse(String userType) {

	public static UserTypeResponse of(String userType) {
		return new UserTypeResponse(userType);
	}
}
