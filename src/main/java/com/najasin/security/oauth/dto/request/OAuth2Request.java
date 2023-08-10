package com.najasin.security.oauth.dto.request;

import com.najasin.domain.user.entity.Oauth2Entity;
import com.najasin.domain.user.entity.enums.Provider;

import lombok.Builder;

public record OAuth2Request(Provider provider, String providerId, String name, String email) {

	@Builder
	public OAuth2Request {
	}

	public Oauth2Entity toOauth2Entity() {
		return Oauth2Entity.builder()
			.provider(provider)
			.providerId(providerId)
			.providerUsername(name)
			.email(email)
			.build();
	}
}
