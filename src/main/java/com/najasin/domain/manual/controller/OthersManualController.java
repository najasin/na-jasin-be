package com.najasin.domain.manual.controller;

import static com.najasin.domain.manual.dto.message.ManualMessage.*;
import static com.najasin.global.response.ApiResponse.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.najasin.domain.manual.dto.request.OthersManualCreateRequest;
import com.najasin.domain.manual.dto.response.JffOtherManualResponse;
import com.najasin.domain.manual.service.CommentService;
import com.najasin.domain.manual.service.OthersManualService;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.service.UserService;
import com.najasin.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{userType}/others-manual")
public class OthersManualController {
	private final OthersManualService othersManualService;
	private final UserService userService;
	private final CommentService commentService;

	@GetMapping
	public ResponseEntity<ApiResponse<JffOtherManualResponse>> getOthersManual(
		@PathVariable String userType,
		@RequestParam(required = true, value = "userId") String userId) {
		return ResponseEntity.ok(createSuccessWithData(
			FIND_OTHERS_MANUAL_SUCCESS.getMsg(),
			othersManualService.getJffOtherManual(userType, userId)));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<?>> saveOthersManual(
		@PathVariable String userType,
		@RequestParam(required = true, value = "userId") String userId,
		@RequestBody OthersManualCreateRequest request) {
		User user = userService.findById(userId);

		othersManualService.saveOthersManual(request, userType, user);
		return null;
	}
}
