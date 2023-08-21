package com.najasin.domain.manual.question.entity;

import com.najasin.domain.manual.dto.param.JffMyQuestion;
import com.najasin.domain.user.entity.userType.UserType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(name = "question")
    private String question;

    @Column(name = "question_type")
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_type_id", referencedColumnName = "user_type_id")
    private UserType userType;

    public JffMyQuestion toJffMyQuestion() {
        return new JffMyQuestion(id, question);
    }
}