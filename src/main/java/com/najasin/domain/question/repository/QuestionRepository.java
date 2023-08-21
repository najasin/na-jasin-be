package com.najasin.domain.question.repository;

import com.najasin.domain.question.entity.Question;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.user.entity.userType.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> getQuestionsByQuestionTypeAndUserType(QuestionType questionType, UserType userType);
}
