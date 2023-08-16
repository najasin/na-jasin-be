package com.najasin.security.oauth.service;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.najasin.domain.user.entity.enums.Provider;
import com.najasin.domain.user.service.UserService;
import com.najasin.security.oauth.common.mapper.AttributeMapperFactory;
import com.najasin.security.oauth.common.mapper.PrincipalUserMapper;
import com.najasin.security.model.OAuth2Request;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOidcUserService extends OidcUserService {
	private final UserService userService;
	private final AttributeMapperFactory attributeMapperFactory;
	private final PrincipalUserMapper principalUserMapper;

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = super.loadUser(generateNewOidcUserRequest(userRequest));
		Provider socialProvider = Provider.valueOf(userRequest.getClientRegistration().getClientName().toUpperCase());

		OAuth2Request oAuth2Request = attributeMapperFactory.getAttributeMapper(socialProvider)
			.mapToDto(oidcUser.getAttributes());

		return principalUserMapper.toPrincipalUser(userService.saveIfNewUser(oAuth2Request));
	}

	private OidcUserRequest generateNewOidcUserRequest(OidcUserRequest userRequest) {
		ClientRegistration clientRegistration = ClientRegistration.withClientRegistration(
			userRequest.getClientRegistration()).userNameAttributeName("sub").build();

		return new OidcUserRequest(clientRegistration,
			userRequest.getAccessToken(),
			userRequest.getIdToken(),
			userRequest.getAdditionalParameters());
	}
}
