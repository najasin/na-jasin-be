package com.najasin.global.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.najasin.global.response.ApiResponse;
import com.najasin.security.jwt.exception.JwtBlackListException;
import com.najasin.security.jwt.exception.JwtExpirationException;
import com.najasin.security.jwt.exception.JwtNotSupportException;
import com.najasin.security.jwt.exception.JwtWrongException;
import com.najasin.security.jwt.exception.JwtWrongSignatureException;

@RestControllerAdvice(basePackages = {"com.najasin.security.jwt.util"})
public class JwtControllerAdvice {
	@ExceptionHandler(JwtWrongSignatureException.class)
	public ResponseEntity<ApiResponse<?>> wrongSignatureExHandler(JwtWrongSignatureException exception) {
		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body(ApiResponse.createFail(exception.getMessage()));
	}

	@ExceptionHandler(JwtExpirationException.class)
	public ResponseEntity<ApiResponse<?>> expireExHandler(JwtExpirationException exception) {
		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body(ApiResponse.createFail(exception.getMessage()));
	}

	@ExceptionHandler(JwtNotSupportException.class)
	public ResponseEntity<ApiResponse<?>> notSupportExHandler(JwtNotSupportException exception) {
		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body(ApiResponse.createFail(exception.getMessage()));
	}

	@ExceptionHandler(JwtWrongException.class)
	public ResponseEntity<ApiResponse<?>> wrongExHandler(JwtWrongException exception) {
		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body(ApiResponse.createFail(exception.getMessage()));
	}

	@ExceptionHandler(JwtBlackListException.class)
	public ResponseEntity<ApiResponse<?>> blackListExHandler(JwtBlackListException exception) {
		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body(ApiResponse.createFail(exception.getMessage()));
	}
}
