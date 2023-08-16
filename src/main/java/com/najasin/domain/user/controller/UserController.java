package com.najasin.domain.user.controller;

import com.najasin.domain.answer.entity.Answer;
import com.najasin.domain.answer.service.AnswerService;
import com.najasin.domain.dto.AnswerDTO;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.user.dto.MyPage;
import com.najasin.domain.user.dto.PutAnswer;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.userKeyword.entity.UserKeyword;
import com.najasin.domain.userUserType.entity.UserUserType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

	@PostMapping("logout")
	public ResponseEntity<ApiResponse<?>> logout(@AccessToken String accessToken, @RefreshToken String refreshToken) {
		userService.logout(accessToken, refreshToken);

		return new ResponseEntity<>(
				ApiResponse.createSuccess(UserResponse.SUCCESS_LOGOUT.getMessage()),
				HttpStatus.OK
		);
	}

	@PutMapping("/{userTypeId}/answers")
	public ResponseEntity<ApiResponse<?>> putAnswers(
			@PathVariable String userTypeId,
			@RequestBody PutAnswer putAnswer
//			@AuthenticationPrincipal UserDetails userDetails
	) {
//		String userId = userDetails.getUsername();
		String userId = "1";
		answerService.deleteAnswers(userId);
		for (AnswerDTO dto : putAnswer.getAnswers()) {
			answerService.save(userId, dto.getId(), dto.getAnswer());
		}
		return new ResponseEntity<>(
				ApiResponse.createSuccess(UserResponse.SUCCESS_UPDATE.getMessage()),
				HttpStatus.OK
		);
	}

	@GetMapping("/{userType}/mypage")
	public ResponseEntity<ApiResponse<?>> getMyPage(@PathVariable String userType, @RequestParam String userId) {

		MyPage myPage = new MyPage();
		User user = userService.findById(userId);

		List<String> userTypes = new ArrayList<>();
		for (UserUserType userUserType : user.getUserUserTypes()) {
			userTypes.add(userUserType.getUserType().getName());
		}
		myPage.setUserTypes(userTypes);

		myPage.setNickname("임시 닉네임");
		myPage.setBaseImage("임시 베이스 이미지 url");
		MyPage.CharacterItem face = new MyPage.CharacterItem(user.getFace().getId(), user.getFace().getLayout_url(), user.getFace().getShow_url());
		MyPage.CharacterItem body = new MyPage.CharacterItem(user.getBody().getId(), user.getBody().getLayout_url(), user.getBody().getShow_url());
		MyPage.CharacterItem expression = new MyPage.CharacterItem(user.getExpression().getId(), user.getExpression().getLayout_url(), user.getExpression().getShow_url());
		myPage.setCharacterItems(new MyPage.CharacterItems(face, body, expression));

		List<MyPage.QAPair> myQaPairs = new ArrayList<>();
		List<MyPage.QAPair> othersQaPairs = new ArrayList<>();
		for (Answer answer : user.getAnswers()) {
			MyPage.QAPair qaPair = new MyPage.QAPair(answer.getQuestion().getId(), answer.getQuestion().getQuestion(), answer.getAnswer());
			if (answer.getQuestion().getQuestionType() == QuestionType.FOR_USER) {
				myQaPairs.add(qaPair);
			} else {
				othersQaPairs.add(qaPair);
			}
		}
		myPage.setMyManualQAPair(myQaPairs);
		myPage.setOthersManualQAPair(othersQaPairs);

		Map<String, Integer> originKeywordPercents = new HashMap<>();
		Map<String, Long> otherKeywordPercents = new HashMap<>();
		for (UserKeyword UK : user.getUserKeywords()) {
			String keyword = UK.getKeyword().getName();
			Integer originPercent = UK.getOriginPercent();
			Long otherPercent = Long.valueOf(UK.getOriginPercent() + UK.getOthersPercent()) / (UK.getOthersCount() + 1);
			originKeywordPercents.put(keyword, originPercent);
			otherKeywordPercents.put(keyword, otherPercent);
		}
		myPage.setOriginKeywordPercents(originKeywordPercents);
		myPage.setOtherKeywordPercents(otherKeywordPercents);

		return new ResponseEntity<>(
				ApiResponse.createSuccessWithData(UserResponse.SUCCESS_GET_PAGE.getMessage(), myPage),
				HttpStatus.OK
		);

	}
}
