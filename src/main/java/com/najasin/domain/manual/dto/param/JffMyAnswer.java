package com.najasin.domain.manual.dto.param;

import com.najasin.domain.manual.answer.entity.Answer;
import com.najasin.domain.manual.question.entity.Question;
import com.najasin.domain.user.entity.User;

public record JffMyAnswer(Long id,
						  String answer) {

	public Answer toAnswerEntity(User user, Question question) {
		return new Answer(user, question, answer);
	}
}
