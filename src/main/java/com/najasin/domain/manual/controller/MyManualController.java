package com.najasin.domain.manual.controller;

import static com.najasin.domain.manual.dto.message.ManualMessage.*;
import static com.najasin.global.response.ApiResponse.*;

import java.util.List;

import com.najasin.domain.manual.entity.question.QuestionType;
import com.najasin.domain.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.najasin.domain.character.dto.param.CharacterItemsParam;
import com.najasin.domain.character.service.CharacterService;
import com.najasin.domain.manual.service.AnswerService;
import com.najasin.domain.manual.dto.param.JffKeywordParam;
import com.najasin.domain.manual.dto.request.MyManualCreateRequest;
import com.najasin.domain.manual.dto.response.JffMyManualCreateResponse;
import com.najasin.domain.manual.dto.response.JffMyManualResponse;
import com.najasin.domain.manual.dto.param.JffQuestionParam;
import com.najasin.domain.manual.entity.keyword.Keyword;
import com.najasin.domain.manual.service.KeywordService;
import com.najasin.domain.manual.entity.question.Question;
import com.najasin.domain.manual.service.QuestionService;
import com.najasin.domain.manual.service.UserKeywordService;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.entity.userType.UserUserType;
import com.najasin.domain.user.service.UserUserTypeService;
import com.najasin.global.annotation.AuthorizeUser;
import com.najasin.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{userType}/my-manual")
public class MyManualController {
	private final CharacterService characterService;
	private final QuestionService questionService;
	private final KeywordService keywordService;
	private final AnswerService answerService;
	private final UserKeywordService userKeywordService;
	private final UserUserTypeService userUserTypeService;
	private final UserService userService;
	@Value("${base-image}")
	private String baseImage;

	@GetMapping
	public ResponseEntity<ApiResponse<JffMyManualResponse>> getMyManual(@PathVariable String userType) {
		CharacterItemsParam characterItems = characterService.findAllItems();

		// 추후에 다른 분기에 대한 처리 필요
		List<JffQuestionParam> questionList = questionService.mapToJffQuestions(questionService.findAll(userType, QuestionType.FOR_USER));
		List<JffKeywordParam> keywordList = keywordService.findAll();

		return ResponseEntity.ok(createSuccessWithData(
				FIND_MY_MANUAL_SUCCESS.getMsg(),
				JffMyManualResponse.of(baseImage, characterItems, questionList, keywordList)));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<?>> saveMyManual(
			@AuthorizeUser User user,
			@PathVariable String userType,
			@RequestBody MyManualCreateRequest body) {
		try {
			userUserTypeService.findByUserIdAndUserTypeName(user.getId(), userType);
			return new ResponseEntity<>(
					createFail(CREATE_MY_MANUAL_FAIL.getMsg()),
					HttpStatus.CONFLICT);
		} catch (EntityNotFoundException e) {
			UserUserType saveUserType = userUserTypeService.save(user, userType, body.nickname());
			userUserTypeService.updateCharacter(saveUserType, body.characterItems());
			userUserTypeService.updateUserType(user, userType);
			userService.updateLastUserType(user, userType);
			List<Question> questions = questionService.findAllByIdList(body.getQuestionIdList());
			answerService.saveAll(body.answers(), questions, user);

			List<Keyword> keywords = keywordService.findAllByIdList(body.getKeywordIdList());
			userKeywordService.saveAll(body.keywordPercents(), keywords, user);


			return new ResponseEntity<>(
					createSuccessWithData(
							CREATE_MY_MANUAL_SUCCESS.getMsg(),
							JffMyManualCreateResponse.of(user.getId(), userType)),
					HttpStatus.CREATED
			);
		}
	}
}
