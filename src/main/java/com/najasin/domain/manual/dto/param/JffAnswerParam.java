package com.najasin.domain.manual.dto.param;

import com.najasin.domain.manual.entity.answer.Answer;
import com.najasin.domain.manual.entity.question.Question;
import com.najasin.domain.user.entity.User;

public record JffMyAnswer(Long id,
						  String answer) {

	public Answer toAnswerEntity(User user, Question question) {
		return new Answer(user, question, answer);
	}
}
