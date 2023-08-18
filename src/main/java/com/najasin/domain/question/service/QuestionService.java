package com.najasin.domain.question.service;

import com.najasin.domain.question.entity.Question;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.question.repository.QuestionRepository;
import com.najasin.domain.user.dto.Page;
import com.najasin.domain.userType.entity.UserType;
import com.najasin.domain.userType.repository.UserTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserTypeRepository userTypeRepository;
    @Transactional
    public List<Page.Questions> getQuestionByQuestionTypeAndUserType(QuestionType questionType, String userTypeName) {
        List<Page.Questions> ret = new ArrayList<>();
        UserType userType = userTypeRepository.findByName(userTypeName).orElseThrow(EntityNotFoundException::new);
        for (Question question : questionRepository.getQuestionsByQuestionTypeAndUserType(questionType, userType)) {
            ret.add(new Page.Questions(question.getId(), question.getQuestion()));
        }
        return ret;
    }
}
