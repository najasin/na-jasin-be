package com.najasin.domain.question.service;

import com.najasin.domain.question.entity.Question;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.question.repository.QuestionRepository;
import com.najasin.domain.userType.entity.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional
    public List<Question> getQuestionByQuestionTypeAndUserType(QuestionType questionType, UserType userType) {
        return questionRepository.getQuestionsByQuestionTypeAndUserType(questionType, userType);
    }
}
