package com.najasin.domain.user.controller;

import static com.najasin.domain.user.dto.message.UserResponse.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.najasin.domain.user.dto.message.UserResponse;
import com.najasin.domain.user.dto.response.AccessTokenGenerateResponse;
import com.najasin.domain.user.service.AuthService;
import com.najasin.domain.user.service.UserService;
import com.najasin.global.annotation.AccessToken;
import com.najasin.global.annotation.RefreshToken;
import com.najasin.global.response.ApiResponse;
import com.najasin.security.jwt.exception.JwtBlackListException;
import com.najasin.security.jwt.exception.JwtExpirationException;
import com.najasin.security.jwt.exception.JwtNotSupportException;
import com.najasin.security.jwt.exception.JwtWrongException;
import com.najasin.security.jwt.exception.JwtWrongSignatureException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")

public class AuthController {
	private final UserService userService;
	private final AuthService authService;

	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<?>> logout(@AccessToken String accessToken, @RefreshToken String refreshToken) {
		userService.logout(accessToken, refreshToken);

		return new ResponseEntity<>(
			ApiResponse.createSuccess(UserResponse.SUCCESS_LOGOUT.getMessage()),
			HttpStatus.OK
		);
	}

	@PostMapping("/accessToken")
	public ResponseEntity<ApiResponse<AccessTokenGenerateResponse>> regenerateAccessToken(
		@RefreshToken String refreshToken) throws JwtNotSupportException, JwtWrongSignatureException,
		JwtExpirationException, JwtWrongException, JwtBlackListException {

		String accessToken = authService.recreateAccessToken(refreshToken);
		return new ResponseEntity<>(
			ApiResponse.createSuccessWithData(
				SUCCESS_RECREATE_ACCESS_TOKEN.getMessage(),
				new AccessTokenGenerateResponse(accessToken)),
			HttpStatus.CREATED
		);
	}
}
