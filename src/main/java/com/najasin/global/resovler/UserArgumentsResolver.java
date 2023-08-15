package com.najasin.global.resovler;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.najasin.domain.user.entity.User;
import com.najasin.global.annotation.AuthorizeUser;
import com.najasin.security.model.PrincipalUser;

@Component
public class UserArgumentsResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(AuthorizeUser.class)
			&& User.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public User resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		PrincipalUser principalUser = (PrincipalUser)SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();
		return principalUser.getUser();
	}
}
