package com.najasin.domain.manual.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.najasin.domain.manual.entity.answer.Answer;
import com.najasin.domain.manual.repository.AnswerRepository;
import com.najasin.domain.manual.dto.param.JffAnswerParam;
import com.najasin.domain.manual.entity.question.Question;
import com.najasin.domain.user.dto.param.AnswerUpdateParam;
import com.najasin.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
	private final AnswerRepository answerRepository;

	@Transactional
	public void saveAll(List<JffAnswerParam> answers, List<Question> questions, User user) {
		answers.sort(Comparator.comparing(JffAnswerParam::id));

		for (int i = 0; i < answers.size(); i++) {
			save(answers.get(i).toAnswerEntity(user, questions.get(i)));
		}
	}

	@Transactional
	public Answer save(Answer answer) {
		return answerRepository.save(answer);
	}

	@Transactional
	public void updateAnswer(List<AnswerUpdateParam> updateAnswers, String userId, String userType) {
		List<Answer> answers = findByUserIdAndUserType(userId, userType);

		answers.sort(Comparator.comparing(Answer::getQuestionId));
		updateAnswers.sort(Comparator.comparing(AnswerUpdateParam::id));

		for(int i=0 ; i<updateAnswers.size() ; i++) {
			answers.get(i).updateAnswer(updateAnswers.get(i).answer());
		}
	}

	@Transactional(readOnly = true)
	public List<Answer> findByUserIdAndUserType(String userId, String userType) {
		List<Answer> answers = answerRepository.findByUserId(userId);

		return answers.stream()
			.filter((answer -> answer.getQuestion().getQuestion().equals(userType.toUpperCase())))
			.toList();
	}
}
