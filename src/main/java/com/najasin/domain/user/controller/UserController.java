package com.najasin.domain.user.controller;

import com.najasin.domain.answer.entity.Answer;
import com.najasin.domain.answer.service.AnswerService;
import com.najasin.domain.answer.dto.AnswerDTO;
import com.najasin.domain.character.CharacterService;
import com.najasin.domain.character.dto.AllCharacterItems;
import com.najasin.domain.character.dto.CharacterInfoDTO;
import com.najasin.domain.comment.repository.CommentRepository;
import com.najasin.domain.comment.service.CommentService;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.question.service.QuestionService;
import com.najasin.domain.user.dto.*;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.userKeyword.entity.UserKeyword;
import com.najasin.domain.userKeyword.service.UserKeywordService;
import com.najasin.domain.userType.entity.UserType;
import com.najasin.domain.userType.repository.UserTypeRepository;
import com.najasin.domain.userUserType.entity.UserUserType;
import com.najasin.domain.userUserType.entity.UserUserTypeId;
import com.najasin.domain.userUserType.repository.UserUserTypeRepository;
import com.najasin.domain.userUserType.service.UserUserTypeService;
import com.najasin.global.annotation.AuthorizeUser;
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
	private final CommentService commentService;
	private final UserUserTypeService userUserTypeService;
	private final UserKeywordService userKeywordService;
	private final CharacterService characterService;

	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<?>> logout(@AccessToken String accessToken, @RefreshToken String refreshToken) {
		userService.logout(accessToken, refreshToken);

		return new ResponseEntity<>(
				ApiResponse.createSuccess(UserResponse.SUCCESS_LOGOUT.getMessage()),
				HttpStatus.OK
		);
	}

	@GetMapping("/test")
	public String test(@AuthorizeUser User user) {
		System.out.println(user.toString());
		System.out.println(user.getId());
		System.out.println(user.getNickname());
		return "test";
	}

	@PutMapping("/{userTypeName}/answers")
	public ResponseEntity<ApiResponse<?>> putAnswers(
			@PathVariable String userTypeName,
			@RequestBody PutAnswer putAnswer,
			@AuthorizeUser User user
	) {
//		String userId = userDetails.getUsername();
		String userId = user.getId();
		answerService.deleteAnswers(userId, userTypeName);
		for (AnswerDTO dto : putAnswer.getAnswers()) {
			answerService.save(userId, dto.getId(), dto.getAnswer());
		}
		return new ResponseEntity<>(
				ApiResponse.createSuccess(UserResponse.SUCCESS_UPDATE.getMessage()),
				HttpStatus.OK
		);
	}

	@PutMapping("/{userTypeName}/character")
	public ResponseEntity<ApiResponse<?>> putCharacter(
			@PathVariable String userTypeName,
			@RequestBody CharacterItems dto,
			@AuthorizeUser User user

	) {
		String userId = user.getId();
		userUserTypeService.updateCharacter(userId, userTypeName, dto);
		return new ResponseEntity<>(
				ApiResponse.createSuccess(UserResponse.SUCCESS_UPDATE.getMessage()),
				HttpStatus.OK
		);
	}


	@PutMapping("/{userTypeName}/nickname")
	public ResponseEntity<ApiResponse<?>> putNickname(
			@PathVariable String userTypeName,
			@RequestBody String nickname,
			@AuthorizeUser User user

	) {
		String userId = user.getId();
		userService.updateNickname(userId, nickname);
		return new ResponseEntity<>(
				ApiResponse.createSuccess(UserResponse.SUCCESS_UPDATE.getMessage()),
				HttpStatus.OK
		);
	}

	@PostMapping("/{userTypeName}/my-manual")
	public ResponseEntity<ApiResponse<?>> postMyManual(
			@PathVariable String userTypeName,
			@RequestBody PageUpdateRequestDTO dto,
			@AuthorizeUser User user
	) {
		String userId = user.getId();
		userService.updateNickname(userId, dto.getNickname());
		userUserTypeService.updateCharacter(userId, userTypeName, dto.getCharacterItems());
		answerService.deleteAnswers(userId, userTypeName);
		answerService.updateAnswers(userId, dto.getAnswers());
		userKeywordService.updateByUser(userId, dto.getKeywordPercents());
		return new ResponseEntity<>(
				ApiResponse.createSuccessWithData(UserResponse.SUCCESS_UPDATE.getMessage(), new UserInfoResponse(userId, userTypeName)),
				HttpStatus.OK
		);
	}

	@PostMapping("/{userTypeName}/others-manual")
	public ResponseEntity<ApiResponse<?>> postOthersManual(
			@PathVariable String userTypeName,
			@RequestParam String userId,
			@RequestBody PageUpdateRequestDTO dto
	) {
		for (PageUpdateRequestDTO.AnswerDTO answerDTO : dto.getAnswers()) {
			commentService.save(userId, answerDTO.getId(), dto.getNickname(), answerDTO.getAnswer());
		}
		for (String keyword : dto.getOtherKeywordPercents().keySet()) {
			userKeywordService.updateByOthers(userId, keyword, dto.getOtherKeywordPercents().get(keyword));
		}
		return new ResponseEntity<>(
				ApiResponse.createSuccess(UserResponse.SUCCESS_UPDATE.getMessage()),
				HttpStatus.OK
		);
	}

	@GetMapping("/{userTypeName}/my-manual")
	public ResponseEntity<ApiResponse<?>> getMyManual(
			@PathVariable String userTypeName,
			@AuthorizeUser User user

	) {

		Page page = new Page();
		String userId = user.getId();

		List<String> userTypes = new ArrayList<>();
		for (UserUserType uut : userUserTypeService.getUserUserTypesByUserId(userId)) {
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
	public ResponseEntity<ApiResponse<?>> getMyPage(
			@PathVariable String userTypeName,
			@AuthorizeUser User user
	) {

		String userId = user.getId();
		Page page = new Page();

		List<String> userTypes = new ArrayList<>();
		for (UserUserType uut : userUserTypeService.getUserUserTypesByUserId(userId)) {
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

	@GetMapping("/{userTypeName}/characterItems")
	public ResponseEntity<ApiResponse<?>> getCharacterItems(
			@PathVariable String userTypeName
	){
		AllCharacterItems allCharacterItems = characterService.getAllCharacterItems();
		return new ResponseEntity<>(
				ApiResponse.createSuccessWithData(UserResponse.SUCCESS_GET_PAGE.getMessage(), allCharacterItems),
				HttpStatus.OK
		);
	}
}
