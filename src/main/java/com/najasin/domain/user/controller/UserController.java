package com.najasin.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.dto.message.UserTypeMessage;
import com.najasin.domain.user.dto.request.UserTypeUpdateRequest;
import com.najasin.domain.user.dto.response.UserTypeResponse;
import com.najasin.domain.user.service.UserUserTypeService;
import com.najasin.global.annotation.AuthorizeUser;
import com.najasin.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserUserTypeService userUserTypeService;

	@GetMapping("/{userType}/mypage")
	public ResponseEntity<ApiResponse<?>> getMyPage(
		@AuthorizeUser User user,
		@PathVariable String userType,
		@RequestParam(name = "userId") String userId) {
		return null;
	}

	@PutMapping("/type")
	public ResponseEntity<ApiResponse<UserTypeResponse>> updateUserType(@AuthorizeUser User user,
		@RequestBody @Validated UserTypeUpdateRequest request) {
		String updatedUserType = userUserTypeService.updateUserType(user, request.userType());

		return ResponseEntity.ok(ApiResponse.createSuccessWithData(
			UserTypeMessage.SUCCESS_UPDATE_USER_TYPE.getMsg(),
			UserTypeResponse.of(updatedUserType)));
	}
}
