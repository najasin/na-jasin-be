package com.najasin.security.jwt.exception;

import javax.naming.AuthenticationException;

public class JwtNotSupportException extends AuthenticationException {
	public JwtNotSupportException() {
		super("지원되지 않는 JWT 토큰입니다.");
	}
}
