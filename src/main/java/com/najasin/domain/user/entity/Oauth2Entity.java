package com.najasin.domain.user.entity;

import com.najasin.domain.user.entity.enums.Provider;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Oauth2Entity {
	@Enumerated(EnumType.STRING)
	@Column(name = "provider")
	private Provider provider;

	@Column(name = "provider_id", nullable = false)
	private String providerId;

	@Column(name = "provider_username", nullable = false)
	private String providerUsername;

	@Column(name = "email")
	private String email;

	@Builder
	public Oauth2Entity(Provider provider, String providerId, String providerUsername, String email) {
		this.provider = provider;
		this.providerId = providerId;
		this.providerUsername = providerUsername;
		this.email = email;
	}
}

