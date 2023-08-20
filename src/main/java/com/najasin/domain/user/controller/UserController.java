package com.najasin.domain.user.controller;

import static java.util.Objects.*;

import com.najasin.domain.answer.service.AnswerService;
import com.najasin.domain.answer.dto.AnswerDTO;
import com.najasin.domain.character.CharacterService;
import com.najasin.domain.character.dto.AllCharacterItems;
import com.najasin.domain.character.dto.CharacterItems;
import com.najasin.domain.comment.service.CommentService;
import com.najasin.domain.keyword.service.KeywordService;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.question.service.QuestionService;
import com.najasin.domain.user.dto.*;
import com.najasin.domain.user.dto.message.UserInfoResponse;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.userKeyword.service.UserKeywordService;
import com.najasin.domain.userUserType.entity.UserUserType;
import com.najasin.domain.userUserType.service.UserUserTypeService;
import com.najasin.global.annotation.AuthorizeUser;
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
import java.util.List;

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
	private final KeywordService keywordService;
//	String userId = "63a47bcb-ebb1-4618-b357-fdd6681bd0fc";

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
			@RequestBody PutAnswer putAnswer,
			@AuthorizeUser User user
	) {
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
				ApiResponse.createSuccessWithData(UserResponse.SUCCESS_UPDATE.getMessage(), new UserInfoResponse(userId, userTypeName)),
				HttpStatus.OK
		);
	}

	@GetMapping("/{userTypeName}/my-manual")
	public ResponseEntity<ApiResponse<?>> getMyManual(
			@PathVariable String userTypeName,
			@AuthorizeUser User user
	) {
		Manual manual = new Manual();
		manual.setNickname("");
		if(!isNull(user)) {
			manual.setNickname(user.getId());
		}
		manual.setBaseImage("https://picsum.photos/200/300?random=1");
		manual.setExampleKeywords(keywordService.getAllKeywords());
		manual.setBaseImage("https://picsum.photos/200/300?random=1");
		manual.setCharacterItems(characterService.getAllCharacterItems().getCharacterItems());
		manual.setQuestions(questionService.getQuestionByQuestionTypeAndUserType(QuestionType.FOR_USER, userTypeName));
		return new ResponseEntity<>(
				ApiResponse.createSuccessWithData(UserResponse.SUCCESS_GET_PAGE.getMessage(), manual),
				HttpStatus.OK
		);
	}

	@GetMapping("/{userTypeName}/others-manual")
	public ResponseEntity<ApiResponse<?>> getOthersPage(@PathVariable String userTypeName, @RequestParam String userId) {

		Page page = new Page();
		User user = userService.findById(userId);

		page.setQuestions(questionService.getQuestionByQuestionTypeAndUserType(QuestionType.FOR_OTHERS, userTypeName));
		page.setNickname(user.getNickname());
		page.setBaseImage("https://picsum.photos/200/300?random=1");
		CharacterItems characterInfoDTO = userUserTypeService.getCharacter(userId, userTypeName);
		page.setCharacterItems(new CharacterItems(characterInfoDTO.getFace(), characterInfoDTO.getBody(), characterInfoDTO.getExpression(), characterInfoDTO.getSet()));
		page.setMyManualQAPair(userUserTypeService.getQAByUserIdAndUserTypeForUser(userId, userTypeName, QuestionType.FOR_USER));

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
		page.setBaseImage("https://picsum.photos/200/300?random=1");
		CharacterItems characterInfoDTO = userUserTypeService.getCharacter(userId, userTypeName);
		page.setCharacterItems(new CharacterItems(characterInfoDTO.getFace(), characterInfoDTO.getBody(), characterInfoDTO.getExpression(), characterInfoDTO.getSet()));

		page.setMyManualQAPair(userUserTypeService.getQAByUserIdAndUserTypeForUser(userId, userTypeName, QuestionType.FOR_USER));
		page.setOthersManualQAPair(userUserTypeService.getOtherManualByUserIdAndUserType(userId, userTypeName, QuestionType.FOR_OTHERS));

		page.setOriginKeywordPercents(userKeywordService.getOriginKeywordPercents(userId));
		page.setOtherKeywordPercents(userKeywordService.getOtherKeywordPercents(userId));

		return new ResponseEntity<>(
				ApiResponse.createSuccessWithData(UserResponse.SUCCESS_GET_PAGE.getMessage(), page),
				HttpStatus.OK
		);
	}

	@GetMapping("/characterItems")
	public ResponseEntity<ApiResponse<?>> getCharacterItems(){
		AllCharacterItems allCharacterItems = characterService.getAllCharacterItems();
		return new ResponseEntity<>(
				ApiResponse.createSuccessWithData(UserResponse.SUCCESS_GET_PAGE.getMessage(), allCharacterItems),
				HttpStatus.OK
		);
	}
}
