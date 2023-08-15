package com.najasin.security.jwt.exception;

import javax.naming.AuthenticationException;

public class JwtWrongSignatureException extends AuthenticationException {
	public JwtWrongSignatureException() {
		super("잘못된 JWT 서명입니다.");
	}
}
