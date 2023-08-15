package com.najasin.security.jwt.exception;

import javax.naming.AuthenticationException;

public class JwtWrongException extends AuthenticationException {
	public JwtWrongException() {
		super("JWT 토큰이 잘못되었습니다.");
	}
}