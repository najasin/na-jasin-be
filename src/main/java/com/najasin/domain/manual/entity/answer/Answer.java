package com.najasin.domain.manual.entity.answer;

import com.najasin.domain.manual.entity.question.Question;
import com.najasin.domain.user.dto.param.AnswerParam;
import com.najasin.domain.user.entity.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@IdClass(AnswerId.class)
public class Answer {
	@Id
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "question_id", referencedColumnName = "question_id", insertable = false, updatable = false)
	private Question question;

	@Column(name = "content")
	private String content;

	public Long getQuestionId() {
		return question.getId();
	}

	public void updateAnswer(String content) {
		this.content = content;
	}

	public AnswerParam toMyAnswerParam() {
		return new AnswerParam(question.getId(), question.getQuestion(), content);
	}
}
