package com.najasin.domain.manual.answer.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.najasin.domain.manual.answer.entity.Answer;
import com.najasin.domain.manual.answer.repository.AnswerRepository;
import com.najasin.domain.manual.dto.param.JffMyAnswer;
import com.najasin.domain.manual.question.entity.Question;
import com.najasin.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
	private final AnswerRepository answerRepository;

	@Transactional
	public void saveAll(List<JffMyAnswer> answers, List<Question> questions, User user) {
		answers.sort(Comparator.comparing(JffMyAnswer::id));
		questions.sort(Comparator.comparing(Question::getId));

		for (int i = 0; i < answers.size(); i++) {
			save(answers.get(i).toAnswerEntity(user, questions.get(i)));
		}
	}

	@Transactional
	public Answer save(Answer answer) {
		return answerRepository.save(answer);
	}

	@Transactional(readOnly = true)
	public List<Answer> findByUserId(String userId) {
		return answerRepository.findByUserId(userId);
	}
}
