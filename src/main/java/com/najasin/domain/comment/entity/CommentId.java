package com.najasin.domain.comment.entity;

import com.najasin.domain.manual.question.entity.Question;
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
public class CommentId implements Serializable {
    private User user;
    private Question question;
}
