package com.najasin.domain.question.service;

import com.najasin.domain.question.entity.Question;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.question.repository.QuestionRepository;
import com.najasin.domain.userType.entity.UserType;
import com.najasin.domain.userType.repository.UserTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserTypeRepository userTypeRepository;
    @Transactional
    public List<String> getQuestionByQuestionTypeAndUserType(QuestionType questionType, String userTypeName) {
        List<String> ret = new ArrayList<>();
        UserType userType = userTypeRepository.findByName(userTypeName).orElseThrow(EntityNotFoundException::new);
        for (Question question : questionRepository.getQuestionsByQuestionTypeAndUserType(questionType, userType)) {
            ret.add(question.getQuestion());
        }
        return ret;
    }
}
