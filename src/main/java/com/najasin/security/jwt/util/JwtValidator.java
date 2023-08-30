package com.najasin.security.jwt.util;

import java.security.Key;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.service.UserService;
import com.najasin.global.util.RedisBlackListUtil;
import com.najasin.security.jwt.exception.JwtBlackListException;
import com.najasin.security.jwt.exception.JwtExpirationException;
import com.najasin.security.jwt.exception.JwtNotSupportException;
import com.najasin.security.jwt.exception.JwtWrongException;
import com.najasin.security.jwt.exception.JwtWrongSignatureException;
import com.najasin.security.oauth.common.mapper.PrincipalUserMapper;
import com.najasin.security.model.PrincipalUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtValidator {

	private final Key key;
	private final UserService userService;
	private final PrincipalUserMapper principalUserMapper;
	private final RedisBlackListUtil redisBlackListUtil;

	public Authentication getAuthentication(String accessToken) throws
		JwtWrongSignatureException,
		JwtExpirationException, JwtNotSupportException, JwtWrongException, JwtBlackListException {
		Claims claims = getTokenClaims(accessToken);
		User user = userService.findById(claims.get("id", String.class));
		PrincipalUser principalUser = principalUserMapper.toPrincipalUser(user);

		return new UsernamePasswordAuthenticationToken(principalUser, "", principalUser.getAuthorities());
	}

	public PrincipalUser getPrincipalUser(String token) throws
		JwtNotSupportException,
		JwtWrongSignatureException,
		JwtExpirationException,
		JwtWrongException,
		JwtBlackListException {
		Claims claims = getTokenClaims(token);
		User user = userService.findById(claims.get("id", String.class));
		return principalUserMapper.toPrincipalUser(user);
	}

	private Claims getTokenClaims(String token) throws
		JwtWrongSignatureException,
		JwtExpirationException,
		JwtNotSupportException, JwtWrongException, JwtBlackListException {
		Claims claims;
		try {
			claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();

			if (redisBlackListUtil.hasKeyBlackList(token)) {
				throw new JwtBlackListException();
			}
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			throw new JwtWrongSignatureException();
		} catch (ExpiredJwtException e) {
			throw new JwtExpirationException();
		} catch (UnsupportedJwtException e) {
			throw new JwtNotSupportException();
		} catch (IllegalArgumentException e) {
			throw new JwtWrongException();
		} catch (JwtBlackListException e) {
			throw new JwtBlackListException();
		}

		return claims;
	}
}
