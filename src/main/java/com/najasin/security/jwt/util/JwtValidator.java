package com.najasin.security.jwt.util;

import java.security.Key;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.service.UserService;
import com.najasin.security.oauth.common.mapper.PrincipalUserMapper;
import com.najasin.security.model.PrincipalUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtValidator {

	private final Key key;
	private final UserService userService;
	private final PrincipalUserMapper principalUserMapper;

	public Authentication getAuthentication(String accessToken) {
		Claims claims = getTokenClaims(accessToken);
		User user = userService.findById(claims.get("id", String.class));
		PrincipalUser principalUser = principalUserMapper.toPrincipalUser(user);

		return new UsernamePasswordAuthenticationToken(principalUser, "", principalUser.getAuthorities());
	}

	private Claims getTokenClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
}
