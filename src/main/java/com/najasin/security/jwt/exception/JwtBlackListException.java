package com.najasin.security.jwt.exception;

import javax.naming.AuthenticationException;

public class JwtBlackListException extends AuthenticationException {
	public JwtBlackListException() {
		super("블랙리스트에 추가된 Jwt 토큰입니다.");
	}
}
