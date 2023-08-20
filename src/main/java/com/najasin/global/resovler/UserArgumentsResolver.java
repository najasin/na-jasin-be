package com.najasin.global.resovler;

import static java.util.Objects.*;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.service.UserService;
import com.najasin.global.annotation.AuthorizeUser;
import com.najasin.security.model.PrincipalUser;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserArgumentsResolver implements HandlerMethodArgumentResolver {
	private final UserService userService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(AuthorizeUser.class)
			&& User.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public User resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if(isNull(securityContext)) {
			return null;
		}
		String authentication = securityContext.getAuthentication().getName();

		return userService.findById(authentication);
	}
}
