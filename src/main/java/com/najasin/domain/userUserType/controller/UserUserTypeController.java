package com.najasin.domain.userUserType.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.najasin.domain.user.entity.User;
import com.najasin.domain.userUserType.dto.message.UserTypeMessage;
import com.najasin.domain.userUserType.dto.request.UserTypeUpdateRequest;
import com.najasin.domain.userUserType.dto.response.UserTypeResponse;
import com.najasin.domain.userUserType.service.UserUserTypeService;
import com.najasin.global.annotation.AuthorizeUser;
import com.najasin.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/type")
public class UserUserTypeController {
	private final UserUserTypeService userUserTypeService;

	@PutMapping
	public ResponseEntity<ApiResponse<UserTypeResponse>> modify(@AuthorizeUser User user,
		@RequestBody @Validated UserTypeUpdateRequest request) {
		String updatedUserType = userUserTypeService.updateUserType(user, request.userType());

		return ResponseEntity.ok(ApiResponse.createSuccessWithData(
			UserTypeMessage.SUCCESS_UPDATE_USER_TYPE.getMsg(),
			UserTypeResponse.of(updatedUserType)));
	}
}
