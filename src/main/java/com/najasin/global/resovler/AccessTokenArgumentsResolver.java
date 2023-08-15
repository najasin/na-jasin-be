package com.najasin.global.resovler;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.najasin.domain.user.entity.User;
import com.najasin.global.annotation.AccessToken;
import com.najasin.global.annotation.AuthorizeUser;

public class AccessTokenArgumentsResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(AccessToken.class)
			&& String.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public String resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String authorizationHeader = webRequest.getHeader("Authorization");

		return authorizationHeader.substring(7);
	}
}
