package com.najasin.global.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.najasin.global.response.ApiResponse;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice("com.najasin.domain.user.AuthController")
public class AuthControllerAdvice {
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiResponse<?>> entityNotFoundExHandler(EntityNotFoundException exception) {
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(ApiResponse.createFail(exception.getMessage()));
	}
}
