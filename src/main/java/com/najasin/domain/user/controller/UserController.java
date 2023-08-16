package com.najasin.domain.user.controller;

import com.najasin.domain.answer.entity.Answer;
import com.najasin.domain.answer.service.AnswerService;
import com.najasin.domain.dto.AnswerDTO;
import com.najasin.domain.question.entity.Question;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.question.service.QuestionService;
import com.najasin.domain.user.dto.Page;
import com.najasin.domain.user.dto.PutAnswer;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.userKeyword.entity.UserKeyword;
import com.najasin.domain.userType.entity.UserType;
import com.najasin.domain.userType.repository.UserTypeRepository;
import com.najasin.domain.userUserType.entity.UserUserType;
import com.najasin.domain.userUserType.entity.UserUserTypeId;
import com.najasin.domain.userUserType.repository.UserUserTypeRepository;
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
	private final UserUserTypeRepository userUserTypeRepository;

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

	@GetMapping("/{userTypeName}/others-manual")
	public ResponseEntity<ApiResponse<?>> getOthersPage(@PathVariable String userTypeName, @RequestParam String userId) {
		Page page = new Page();
		User user = userService.findById(userId);
		UserType userType = userTypeRepository.findUserTypeByName(userTypeName);
		UserUserType userUserType = userUserTypeRepository.findById(new UserUserTypeId(user, userType)).orElseThrow(EntityNotFoundException::new);

		List<String> questions = new ArrayList<>();
		for (Question question : questionService.getQuestionByQuestionTypeAndUserType(QuestionType.FOR_OTHERS, userType)) {
			questions.add(question.getQuestion());
		}
		page.setQuestions(questions);

		page.setNickname(user.getNickname());
		page.setBaseImage("임시 베이스 이미지 url");
		Page.CharacterItem face = null; Page.CharacterItem body = null; Page.CharacterItem expression= null; Page.CharacterItem characterSet = null;
		if (userUserType.getSet() != null) {
			characterSet = new Page.CharacterItem(userUserType.getSet().getId(), userUserType.getSet().getUrl(), userUserType.getSet().getUrl());
		} else{
			face = new Page.CharacterItem(userUserType.getFace().getId(), userUserType.getFace().getLayout_url(), userUserType.getFace().getShow_url());
			body = new Page.CharacterItem(userUserType.getBody().getId(), userUserType.getBody().getLayout_url(), userUserType.getBody().getShow_url());
			expression = new Page.CharacterItem(userUserType.getExpression().getId(), userUserType.getExpression().getLayout_url(), userUserType.getExpression().getShow_url());
		}
		page.setCharacterItems(new Page.CharacterItems(face, body, expression, characterSet));

		List<Page.QAPair> myQaPairs = new ArrayList<>();
		for (Answer answer : user.getAnswers()) {
			Page.QAPair qaPair = new Page.QAPair(answer.getQuestion().getId(), answer.getQuestion().getQuestion(), answer.getAnswer());
			if (answer.getQuestion().getQuestionType() == QuestionType.FOR_USER) {
				myQaPairs.add(qaPair);
			}
		}
		page.setMyManualQAPair(myQaPairs);

		Map<String, Integer> originKeywordPercents = new HashMap<>();
		Map<String, Long> otherKeywordPercents = new HashMap<>();
		for (UserKeyword UK : user.getUserKeywords()) {
			String keyword = UK.getKeyword().getName();
			Integer originPercent = UK.getOriginPercent();
			Long otherPercent = Long.valueOf(UK.getOriginPercent() + UK.getOthersPercent()) / (UK.getOthersCount() + 1);
			originKeywordPercents.put(keyword, originPercent);
			otherKeywordPercents.put(keyword, otherPercent);
		}
		page.setOriginKeywordPercents(originKeywordPercents);
		page.setOtherKeywordPercents(otherKeywordPercents);
		return new ResponseEntity<>(
				ApiResponse.createSuccessWithData(UserResponse.SUCCESS_GET_PAGE.getMessage(), page),
				HttpStatus.OK
		);
	}

	@GetMapping("/{userTypeName}/mypage")
	public ResponseEntity<ApiResponse<?>> getMyPage(@PathVariable String userTypeName, @RequestParam String userId) {

		Page page = new Page();
		User user = userService.findById(userId);
		UserType userType = userTypeRepository.findUserTypeByName(userTypeName);
		UserUserType userUserType = userUserTypeRepository.findById(new UserUserTypeId(user, userType)).orElseThrow(EntityNotFoundException::new);

		List<String> userTypes = new ArrayList<>();
		for (UserUserType uut : user.getUserUserTypes()) {
			userTypes.add(uut.getUserType().getName());
		}
		page.setUserTypes(userTypes);

		page.setNickname(user.getNickname());
		page.setBaseImage("임시 베이스 이미지 url");
		Page.CharacterItem face = null; Page.CharacterItem body = null; Page.CharacterItem expression= null; Page.CharacterItem characterSet = null;
		if (userUserType.getSet() != null) {
			characterSet = new Page.CharacterItem(userUserType.getSet().getId(), userUserType.getSet().getUrl(), userUserType.getSet().getUrl());
		} else{
			face = new Page.CharacterItem(userUserType.getFace().getId(), userUserType.getFace().getLayout_url(), userUserType.getFace().getShow_url());
			body = new Page.CharacterItem(userUserType.getBody().getId(), userUserType.getBody().getLayout_url(), userUserType.getBody().getShow_url());
			expression = new Page.CharacterItem(userUserType.getExpression().getId(), userUserType.getExpression().getLayout_url(), userUserType.getExpression().getShow_url());
		}
		page.setCharacterItems(new Page.CharacterItems(face, body, expression, characterSet));

		List<Page.QAPair> myQaPairs = new ArrayList<>();
		List<Page.QAPair> othersQaPairs = new ArrayList<>();
		for (Answer answer : user.getAnswers()) {
			Page.QAPair qaPair = new Page.QAPair(answer.getQuestion().getId(), answer.getQuestion().getQuestion(), answer.getAnswer());
			if (answer.getQuestion().getQuestionType() == QuestionType.FOR_USER) {
				myQaPairs.add(qaPair);
			} else {
				othersQaPairs.add(qaPair);
			}
		}
		page.setMyManualQAPair(myQaPairs);
		page.setOthersManualQAPair(othersQaPairs);

		Map<String, Integer> originKeywordPercents = new HashMap<>();
		Map<String, Long> otherKeywordPercents = new HashMap<>();
		for (UserKeyword UK : user.getUserKeywords()) {
			String keyword = UK.getKeyword().getName();
			Integer originPercent = UK.getOriginPercent();
			Long otherPercent = Long.valueOf(UK.getOriginPercent() + UK.getOthersPercent()) / (UK.getOthersCount() + 1);
			originKeywordPercents.put(keyword, originPercent);
			otherKeywordPercents.put(keyword, otherPercent);
		}
		page.setOriginKeywordPercents(originKeywordPercents);
		page.setOtherKeywordPercents(otherKeywordPercents);

		return new ResponseEntity<>(
				ApiResponse.createSuccessWithData(UserResponse.SUCCESS_GET_PAGE.getMessage(), page),
				HttpStatus.OK
		);

	}
}
