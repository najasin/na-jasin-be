package com.najasin.domain.user.controller;

import com.najasin.domain.answer.entity.Answer;
import com.najasin.domain.answer.service.AnswerService;
import com.najasin.domain.answer.dto.AnswerDTO;
import com.najasin.domain.character.dto.CharacterInfoDTO;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.question.service.QuestionService;
import com.najasin.domain.user.dto.Page;
import com.najasin.domain.user.dto.PutAnswer;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.userKeyword.entity.UserKeyword;
import com.najasin.domain.userKeyword.service.UserKeywordService;
import com.najasin.domain.userType.entity.UserType;
import com.najasin.domain.userType.repository.UserTypeRepository;
import com.najasin.domain.userUserType.entity.UserUserType;
import com.najasin.domain.userUserType.entity.UserUserTypeId;
import com.najasin.domain.userUserType.repository.UserUserTypeRepository;
import com.najasin.domain.userUserType.service.UserUserTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.najasin.domain.user.dto.message.UserResponse;
import com.najasin.domain.user.service.UserService;
import com.najasin.global.annotation.AccessToken;
import com.najasin.global.annotation.RefreshToken;
import com.najasin.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")

public class UserController {
	private final UserService userService;
	private final AnswerService answerService;
	private final QuestionService questionService;
	private final UserTypeRepository userTypeRepository;
	private final UserUserTypeService userUserTypeService;
	private final UserKeywordService userKeywordService;

	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<?>> logout(@AccessToken String accessToken, @RefreshToken String refreshToken) {
		userService.logout(accessToken, refreshToken);

		return new ResponseEntity<>(
				ApiResponse.createSuccess(UserResponse.SUCCESS_LOGOUT.getMessage()),
				HttpStatus.OK
		);
	}

	@PutMapping("/{userTypeName}/answers")
	public ResponseEntity<ApiResponse<?>> putAnswers(
			@PathVariable String userTypeName,
			@RequestBody PutAnswer putAnswer
//			@AuthenticationPrincipal UserDetails userDetails
	) {
//		String userId = userDetails.getUsername();
		String userId = "1";
		UserType userType = userTypeRepository.findUserTypeByName(userTypeName);
		answerService.deleteAnswers(userId, userType);
		for (AnswerDTO dto : putAnswer.getAnswers()) {
			answerService.save(userId, dto.getId(), dto.getAnswer());
		}
		return new ResponseEntity<>(
				ApiResponse.createSuccess(UserResponse.SUCCESS_UPDATE.getMessage()),
				HttpStatus.OK
		);
	}

	@PutMapping("/{userTypeName}/nickname")
	public ResponseEntity<ApiResponse<?>> putNickname(
			@PathVariable String userTypeName,
			@RequestBody String nickname
			//			@AuthenticationPrincipal UserDetails userDetails
	) {
		String userId = "1";
		userService.updateNickname(userId, nickname);
		return new ResponseEntity<>(
				ApiResponse.createSuccess(UserResponse.SUCCESS_UPDATE.getMessage()),
				HttpStatus.OK
		);
	}

	@GetMapping("/{userTypeName}/my-manual")
	public ResponseEntity<ApiResponse<?>> getMyManual(
			@PathVariable String userTypeName
			//		@AuthenticationPrincipal UserDetails userDetails
	) {
		Page page = new Page();
		String userId = "1";
		User user = userService.findById(userId);

		List<String> userTypes = new ArrayList<>();
		for (UserUserType uut : user.getUserUserTypes()) {
			userTypes.add(uut.getUserType().getName());
		}
		page.setUserTypes(userTypes);
		page.setNickname(user.getNickname());
		page.setBaseImage("임시 이미지 url");
		CharacterInfoDTO characterInfoDTO = userUserTypeService.getCharacter(userId, userTypeName);
		page.setCharacterItems(new Page.CharacterItems(characterInfoDTO.getFace(), characterInfoDTO.getBody(), characterInfoDTO.getExpression(), characterInfoDTO.getCharacterSet()));
		page.setQuestions(questionService.getQuestionByQuestionTypeAndUserType(QuestionType.FOR_USER, userTypeName));
		return new ResponseEntity<>(
				ApiResponse.createSuccessWithData(UserResponse.SUCCESS_GET_PAGE.getMessage(), page),
				HttpStatus.OK
		);
	}

	@GetMapping("/{userTypeName}/others-manual")
	public ResponseEntity<ApiResponse<?>> getOthersPage(@PathVariable String userTypeName, @RequestParam String userId) {

		Page page = new Page();
		User user = userService.findById(userId);

		page.setQuestions(questionService.getQuestionByQuestionTypeAndUserType(QuestionType.FOR_OTHERS, userTypeName));
		page.setNickname(user.getNickname());
		page.setBaseImage("임시 베이스 이미지 url");
		CharacterInfoDTO characterInfoDTO = userUserTypeService.getCharacter(userId, userTypeName);
		page.setCharacterItems(new Page.CharacterItems(characterInfoDTO.getFace(), characterInfoDTO.getBody(), characterInfoDTO.getExpression(), characterInfoDTO.getCharacterSet()));
		page.setMyManualQAPair(userUserTypeService.getQAByUserIdAndUserTypeAndQuestionType(userId, userTypeName, QuestionType.FOR_USER));
		page.setOriginKeywordPercents(userKeywordService.getOriginKeywordPercents(userId));
		page.setOtherKeywordPercents(userKeywordService.getOtherKeywordPercents(userId));
		return new ResponseEntity<>(
				ApiResponse.createSuccessWithData(UserResponse.SUCCESS_GET_PAGE.getMessage(), page),
				HttpStatus.OK
		);
	}

	@GetMapping("/{userTypeName}/mypage")
	public ResponseEntity<ApiResponse<?>> getMyPage(@PathVariable String userTypeName, @RequestParam String userId) {

		Page page = new Page();
		User user = userService.findById(userId);

		List<String> userTypes = new ArrayList<>();
		for (UserUserType uut : user.getUserUserTypes()) {
			userTypes.add(uut.getUserType().getName());
		}
		page.setUserTypes(userTypes);

		page.setNickname(user.getNickname());
		page.setBaseImage("임시 베이스 이미지 url");
		CharacterInfoDTO characterInfoDTO = userUserTypeService.getCharacter(userId, userTypeName);
		page.setCharacterItems(new Page.CharacterItems(characterInfoDTO.getFace(), characterInfoDTO.getBody(), characterInfoDTO.getExpression(), characterInfoDTO.getCharacterSet()));

		page.setMyManualQAPair(userUserTypeService.getQAByUserIdAndUserTypeAndQuestionType(userId, userTypeName, QuestionType.FOR_USER));
		page.setOthersManualQAPair(userUserTypeService.getQAByUserIdAndUserTypeAndQuestionType(userId, userTypeName, QuestionType.FOR_OTHERS));

		page.setOriginKeywordPercents(userKeywordService.getOriginKeywordPercents(userId));
		page.setOtherKeywordPercents(userKeywordService.getOtherKeywordPercents(userId));

		return new ResponseEntity<>(
				ApiResponse.createSuccessWithData(UserResponse.SUCCESS_GET_PAGE.getMessage(), page),
				HttpStatus.OK
		);



	}
}
