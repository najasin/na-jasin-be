package com.najasin.security.oauth.dto.response;

import lombok.Builder;

public record OAuth2Response(String accessToken,
							 String refreshToken,
							 String userId,
							 String userType) {
	@Builder
	public OAuth2Response {
	}
}
