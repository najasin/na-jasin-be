package com.najasin.domain.manual.entity.answer;

import com.najasin.domain.manual.entity.question.Question;
import com.najasin.domain.user.entity.User;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerId implements Serializable {
    private User user;
    private Question question;
}
