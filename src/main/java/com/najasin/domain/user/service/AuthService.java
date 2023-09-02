package com.najasin.domain.user.service;

import org.springframework.stereotype.Service;

import com.najasin.security.jwt.exception.JwtBlackListException;
import com.najasin.security.jwt.exception.JwtExpirationException;
import com.najasin.security.jwt.exception.JwtNotSupportException;
import com.najasin.security.jwt.exception.JwtWrongException;
import com.najasin.security.jwt.exception.JwtWrongSignatureException;
import com.najasin.security.jwt.util.JwtProvider;
import com.najasin.security.jwt.util.JwtValidator;
import com.najasin.security.model.PrincipalUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final JwtValidator jwtValidator;
	private final JwtProvider jwtProvider;

	public String recreateAccessToken(String refreshToken) throws
		JwtNotSupportException,
		JwtWrongSignatureException,
		JwtExpirationException,
		JwtWrongException,
		JwtBlackListException {
		PrincipalUser principalUser = jwtValidator.getPrincipalUser(refreshToken);

		return jwtProvider.recreateAccessToken(principalUser);
	}
}
