package com.najasin.domain.manual.service;

import static java.util.Objects.*;

import java.util.Comparator;
import java.util.List;

import com.najasin.domain.manual.entity.question.QuestionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.najasin.domain.manual.dto.param.JffCommentParam;
import com.najasin.domain.manual.dto.param.JffKeywordPercentParam;
import com.najasin.domain.manual.dto.param.JffQuestionParam;
import com.najasin.domain.manual.dto.request.OthersManualCreateRequest;
import com.najasin.domain.manual.dto.response.JffOtherManualResponse;
import com.najasin.domain.manual.entity.answer.Answer;
import com.najasin.domain.manual.entity.question.Question;
import com.najasin.domain.manual.entity.userKeyword.UserKeyword;
import com.najasin.domain.user.dto.param.AnswerParam;
import com.najasin.domain.user.dto.param.CommentParam;
import com.najasin.domain.user.dto.response.MyPageResponse;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.entity.userType.UserType;
import com.najasin.domain.user.entity.userType.UserUserType;
import com.najasin.domain.user.service.UserUserTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OthersManualService {
	private final UserUserTypeService userUserTypeService;
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserKeywordService userKeywordService;
	private final CommentService commentService;
	@Value("${base-image}")
	private String baseImage;

	@Transactional(readOnly = true)
	public JffOtherManualResponse getJffOtherManual(String userType, String userId) {
		UserUserType userUserType = userUserTypeService.findByUserIdAndUserTypeName(userId, userType);
		List<AnswerParam> answers = answerService.findByUserIdAndUserType(userId, userType)
			.stream()
			.map(Answer::toMyAnswerParam)
			.toList();
		List<UserKeyword> percents = userKeywordService.findByUserId(userId);
		List<CommentParam> comments = commentService.mapToCommentParam(commentService.findAllByUserId(userId));
		List<JffQuestionParam> questions = questionService.mapToJffQuestions(questionService.findAll(userType, QuestionType.FOR_OTHERS));

		return JffOtherManualResponse.builder()
			.nickname(userUserType.getNickname())
			.baseImage(baseImage)
			.characterItems(userUserType.toMyCharacterItemsParam())
			.questions(questions)
			.myManualQAPair(answers)
			.othersManualQAPairs(comments)
			.originKeywordPercents(percents.stream().map(UserKeyword::toMyKeywordPercentParam).toList())
			.otherKeywordPercents(percents.stream().map(UserKeyword::toOthersKeywordPercentParam).toList())
			.build();
	}

	@Transactional
	public void saveOthersManual(OthersManualCreateRequest request, String userType, User user) {
		List<Question> questions = questionService.findAll(userType, QuestionType.FOR_OTHERS);

		questions.sort(Comparator.comparing(Question::getId));
		commentService.saveAll(request.answers(), questions, user, request.nickname());

		List<UserKeyword> userKeywords = userKeywordService.findByUserId(user.getId());
		userKeywords.sort(Comparator.comparing(UserKeyword::getKeyWordId));
		userKeywordService.updateAllOthersPercent(request.otherKeywordPercents(), userKeywords);
	}
}
