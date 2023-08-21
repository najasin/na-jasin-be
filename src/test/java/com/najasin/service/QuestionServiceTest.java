package com.najasin.service;

import com.najasin.domain.question.entity.Question;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.question.repository.QuestionRepository;
import com.najasin.domain.question.service.QuestionService;
import com.najasin.domain.user.dto.Page;
import com.najasin.domain.user.entity.userType.UserType;
import com.najasin.domain.user.repository.UserTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static junit.framework.TestCase.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)

public class QuestionServiceTest {
    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private UserTypeRepository userTypeRepository;

    @Test
    @DisplayName("질문 타입과 유저 타입별로 질문을 받아온다")
    public void getQuestionByQuestionTypeAndUserType() {

        //given
        QuestionType questionType = QuestionType.FOR_USER;
        String userTypeName = "JFF";
        UserType userType = new UserType(1L, userTypeName, new ArrayList<>());

        List<Question> mockQuestions = new ArrayList<>();
        mockQuestions.add(new Question(1L, "Question 1", QuestionType.FOR_USER,null, userType));
        mockQuestions.add(new Question(2L, "Question 2", QuestionType.FOR_USER, null, userType));

        when(userTypeRepository.findByName(userTypeName)).thenReturn(Optional.of(userType));
        when(questionRepository.getQuestionsByQuestionTypeAndUserType(questionType, userType)).thenReturn(mockQuestions);

        //when
        List<Page.Questions> ret = questionService.getQuestionByQuestionTypeAndUserType(questionType, userTypeName);

        //then
        assertNotNull(ret);
        assertEquals(2, ret.size());
        assertEquals("Question 1", ret.get(0).getQuestion());
        assertEquals("Question 2", ret.get(1).getQuestion());

        verify(userTypeRepository, times(1)).findByName(userTypeName);
        verify(questionRepository, times(1)).getQuestionsByQuestionTypeAndUserType(questionType, userType);
    }
}
