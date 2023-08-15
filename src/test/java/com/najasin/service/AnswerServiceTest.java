package com.najasin.service;

import com.najasin.domain.answer.entity.Answer;
import com.najasin.domain.answer.repository.AnswerRepository;
import com.najasin.domain.answer.service.AnswerService;
import com.najasin.domain.question.entity.Question;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.question.repository.QuestionRepository;
import com.najasin.domain.user.entity.Oauth2Entity;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.entity.enums.Provider;
import com.najasin.domain.user.repository.UserRepository;
import com.najasin.domain.userType.entity.UserType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {
    @InjectMocks
    private AnswerService answerService;

    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private QuestionRepository questionRepository;


    private String mockUserId;
    private Oauth2Entity mockOauth2Entity;
    private User mockUser;

    private Long mockQuestionId;
    private Question mockQuestion;
    private Answer mockAnswer;
    private String mockAns;



    @BeforeEach
    public void setup(){
        mockUserId = "test-uuid";
        mockOauth2Entity = Oauth2Entity.builder()
                .provider(Provider.KAKAO)
                .providerId("test-provider-id")
                .providerUsername("test-provider-nickname")
                .email("test@kakao.com")
                .build();
        mockUser = new User(mockUserId, mockOauth2Entity);
        mockQuestionId = 123456789L;
        mockQuestion = new Question(mockQuestionId, "테스트 질문", QuestionType.FOR_USER, new ArrayList<>(), new UserType(1L, "JFF", new ArrayList<>()));
        mockAns = "테스트 응답";
        mockAnswer = new Answer(mockUser, mockQuestion, mockAns);
    }

    @AfterEach
    public void cleanup(){
        userRepository.deleteAll();
        questionRepository.deleteAll();
        answerRepository.deleteAll();
    }

    @Test
    @DisplayName("유저가 질문에 답을 한다")
    public void save(){
        given(answerRepository.save(any()))
                .willReturn(mockAnswer);
        given(userRepository.findById(any()))
                .willReturn(Optional.of(mockUser));
        given(questionRepository.findById(any()))
                .willReturn(Optional.of(mockQuestion));
        Answer saveAnswer = answerService.save(mockUserId, mockQuestionId, mockAns);
        assertEquals(saveAnswer, mockAnswer);
    }
}
