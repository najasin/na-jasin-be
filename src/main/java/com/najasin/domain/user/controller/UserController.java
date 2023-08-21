package com.najasin.domain.user.controller;

import static com.najasin.domain.user.dto.message.UserResponse.*;
import static com.najasin.global.response.ApiResponse.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.najasin.domain.manual.service.AnswerService;
import com.najasin.domain.user.dto.request.AnswerUpdateRequest;
import com.najasin.domain.user.dto.request.CharacterUpdateRequest;
import com.najasin.domain.user.dto.request.NicknameUpdateRequest;
import com.najasin.domain.user.dto.response.MyPageResponse;
import com.najasin.domain.user.dto.response.UserTypeUpdateResponse;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.dto.message.UserTypeMessage;
import com.najasin.domain.user.dto.request.UserTypeUpdateRequest;
import com.najasin.domain.user.entity.userType.UserUserType;
import com.najasin.domain.user.service.UserUserTypeService;
import com.najasin.global.annotation.AuthorizeUser;
import com.najasin.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserUserTypeService userUserTypeService;
	private final AnswerService answerService;
	@Value("${base-image}")
	private String baseImage;

	@GetMapping("/user/{userType}/mypage")
	public ResponseEntity<ApiResponse<MyPageResponse>> getMyPage(
		@AuthorizeUser User user,
		@PathVariable String userType,
		@RequestParam(name = "userId") String userId) {
		MyPageResponse response = userUserTypeService.getMyPage(user, userType, userId);

		return ResponseEntity.ok(createSuccessWithData(SUCCESS_GET_MY_PAGE.getMessage(), response));
	}

	@PutMapping("/type")
	public ResponseEntity<ApiResponse<UserTypeUpdateResponse>> updateUserType(
		@AuthorizeUser User user,
		@RequestBody @Validated UserTypeUpdateRequest request) {
		UserTypeUpdateResponse response = userUserTypeService.updateUserType(user, request.userType());

		return ResponseEntity.ok(createSuccessWithData(
			UserTypeMessage.SUCCESS_UPDATE_USER_TYPE.getMsg(),
			response));
	}

	@PutMapping("/{userType}/nickname")
	public ResponseEntity<ApiResponse<?>> updateNickname(
		@AuthorizeUser User user,
		@PathVariable String userType,
		@RequestBody @Validated NicknameUpdateRequest request) {
		UserUserType userUserType = userUserTypeService.findByUserIdAndUserTypeName(user.getId(), userType);
		userUserTypeService.updateNickname(userUserType, request.nickname());

		return ResponseEntity.ok(createSuccess(SUCCESS_UPDATE_NICKNAME.getMessage()));
	}

	@PutMapping("/{userType}/character")
	public ResponseEntity<ApiResponse<?>> updateCharacter(
		@AuthorizeUser User user,
		@PathVariable String userType,
		@RequestBody CharacterUpdateRequest request) {
		UserUserType userUserType = userUserTypeService.findByUserIdAndUserTypeName(user.getId(), userType);
		userUserTypeService.updateCharacter(userUserType, request.toManualCharacterItems());

		return ResponseEntity.ok(createSuccess(SUCCESS_UPDATE_CHARACTER.getMessage()));
	}

	@PutMapping("/{userType}/answer")
	public ResponseEntity<ApiResponse<?>> updateAnswer(
		@AuthorizeUser User user,
		@PathVariable String userType,
		@RequestBody AnswerUpdateRequest request) {
		answerService.updateAnswer(request.answer(), user.getId(), userType);

		return ResponseEntity.ok(createSuccess(SUCCESS_UPDATE_ANSWER.getMessage()));
	}
}
