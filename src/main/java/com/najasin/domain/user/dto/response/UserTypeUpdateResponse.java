package com.najasin.domain.user.dto.response;

public record UserTypeUpdateResponse(String userType,
									 Boolean existFlag) {

	public static UserTypeUpdateResponse of(String userType, Boolean existFlag) {
		return new UserTypeUpdateResponse(userType, existFlag);
	}
}
