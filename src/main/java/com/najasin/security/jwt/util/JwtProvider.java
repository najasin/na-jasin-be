package com.najasin.security.jwt.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.najasin.security.jwt.dto.JwtToken;
import com.najasin.security.model.PrincipalUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtProvider {
	private static final Long ACCESS_TOKEN_VALIDATION_SECOND = 60L * 60 * 24 * 1000; // 24hours
	private static final Long REFRESH_TOKEN_VALIDATION_SECOND = 60L * 60 * 24 * 1000 * 7; // 7days
	private static final String BEARER_TYPE = "bearer";

	private final Key key;

	public JwtToken createJwtToken(PrincipalUser principalUser) {
		Claims claims = getClaims(principalUser);

		String accessToken = getToken(principalUser, claims, ACCESS_TOKEN_VALIDATION_SECOND);
		String refreshToken = getToken(principalUser, claims, REFRESH_TOKEN_VALIDATION_SECOND);

		return new JwtToken(accessToken, refreshToken, BEARER_TYPE);
	}

	public Claims getClaims(PrincipalUser principalUser) {
		Claims claims = Jwts.claims();
		claims.put("id", principalUser.getName());
		return claims;
	}

	private String getToken(PrincipalUser principalUser, Claims claims, Long validationSecond) {
		long now = new Date().getTime();

		return Jwts.builder()
			.setSubject(principalUser.getName())
			.setClaims(claims)
			.signWith(key, SignatureAlgorithm.HS512)
			.setExpiration(new Date(now + validationSecond))
			.compact();
	}
}
