package com.najasin.domain.manual.repository;

import com.najasin.domain.manual.entity.question.Question;
import com.najasin.domain.manual.entity.question.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByQuestionTypeAndUserTypeName(QuestionType questionType, String userTypeName);
}
