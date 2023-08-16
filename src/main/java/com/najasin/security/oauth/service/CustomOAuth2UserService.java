package com.najasin.security.oauth.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.najasin.domain.user.entity.enums.Provider;
import com.najasin.domain.user.service.UserService;
import com.najasin.security.oauth.common.mapper.AttributeMapperFactory;
import com.najasin.security.oauth.common.mapper.PrincipalUserMapper;
import com.najasin.security.model.OAuth2Request;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserService userService;
	private final AttributeMapperFactory attributeMapperFactory;
	private final PrincipalUserMapper principalUserMapper;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		Provider provider = Provider.valueOf(userRequest.getClientRegistration().getClientName().toUpperCase());
		OAuth2Request oAuth2Request = attributeMapperFactory.getAttributeMapper(provider)
			.mapToDto(oAuth2User.getAttributes());

		return principalUserMapper.toPrincipalUser(userService.saveIfNewUser(oAuth2Request));
	}
}
