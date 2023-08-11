package com.najasin.domain.answer.entity;

import com.najasin.domain.keyword.entity.Keyword;
import com.najasin.domain.question.entity.Question;
import com.najasin.domain.user.entity.User;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class AnswerID implements Serializable {
    private User user;
    private Question question;
}
