package com.najasin.domain.manual.question.repository;

import com.najasin.domain.manual.question.entity.Question;
import com.najasin.domain.manual.question.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByQuestionTypeAndUserTypeName(QuestionType questionType, String userTypeName);
}
