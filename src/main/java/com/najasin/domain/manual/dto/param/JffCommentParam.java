package com.najasin.domain.manual.dto.param;

import com.najasin.domain.manual.entity.comment.Comment;
import com.najasin.domain.manual.entity.question.Question;
import com.najasin.domain.user.entity.User;

public record JffCommentParam(Long id,
							  String answer) {
	public Comment toCommentEntity(User user, Question question, String nickname) {
		return Comment.builder()
			.user(user)
			.question(question)
			.content(answer)
			.nickname(nickname)
			.build();
	}
}
