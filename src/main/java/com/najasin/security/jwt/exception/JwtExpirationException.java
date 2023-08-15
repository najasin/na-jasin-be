package com.najasin.security.jwt.exception;

import javax.naming.AuthenticationException;

public class JwtExpirationException extends AuthenticationException {
	public JwtExpirationException() {
		super("만료된 JWT 토큰입니다.");
	}
}
