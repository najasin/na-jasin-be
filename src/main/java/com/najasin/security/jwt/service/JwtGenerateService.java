package com.najasin.security.jwt.service;

import org.springframework.stereotype.Service;

import com.najasin.security.jwt.dto.JwtToken;
import com.najasin.security.jwt.util.JwtProvider;
import com.najasin.security.model.PrincipalUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtGenerateService {
	private final JwtProvider jwtProvider;

	public JwtToken createJwtToken(PrincipalUser principalUser) {
		return jwtProvider.createJwtToken(principalUser);
	}
}
