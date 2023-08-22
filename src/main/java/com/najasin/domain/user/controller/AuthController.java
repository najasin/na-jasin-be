package com.najasin.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.najasin.domain.user.dto.message.UserResponse;
import com.najasin.domain.user.service.UserService;
import com.najasin.global.annotation.AccessToken;
import com.najasin.global.annotation.RefreshToken;
import com.najasin.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")

public class AuthController {
	private final UserService userService;

	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<?>> logout(@AccessToken String accessToken, @RefreshToken String refreshToken) {
		userService.logout(accessToken, refreshToken);

		return new ResponseEntity<>(
			ApiResponse.createSuccess(UserResponse.SUCCESS_LOGOUT.getMessage()),
			HttpStatus.OK
		);
	}
}
