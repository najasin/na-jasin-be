package com.najasin.service;

import com.najasin.domain.answer.entity.Answer;
import com.najasin.domain.keyword.entity.Keyword;
import com.najasin.domain.keyword.repository.KeywordRepository;
import com.najasin.domain.question.entity.Question;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.user.entity.Oauth2Entity;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.entity.enums.Provider;
import com.najasin.domain.user.repository.UserRepository;
import com.najasin.domain.userKeyword.entity.UserKeyword;
import com.najasin.domain.userKeyword.repository.UserKeywordRepository;
import com.najasin.domain.userKeyword.service.UserKeywordService;
import com.najasin.domain.userType.entity.UserType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserKeywordServiceTest {
    @InjectMocks
    private UserKeywordService userKeywordService;

    @Mock
    private UserKeywordRepository userKeywordRepository;
    @Mock
    private KeywordRepository keywordRepository;
    @Mock
    private UserRepository userRepository;

    private String mockUserId;
    private Oauth2Entity mockOauth2Entity;
    private User mockUser;

    private Long mockKeywordId;
    private Keyword mockKeyword;

    private UserKeyword mockUK;
    private int mockPercent;

    @BeforeEach
    public void setup() {
        mockUserId = "test-uuid";
        mockOauth2Entity = Oauth2Entity.builder()
                .provider(Provider.KAKAO)
                .providerId("test-provider-id")
                .providerUsername("test-provider-nickname")
                .email("test@kakao.com")
                .build();
        mockUser = new User(mockUserId, mockOauth2Entity);
        mockKeywordId = 123456789L;
        mockKeyword = new Keyword(mockKeywordId, "키워드");
        mockPercent = 50;
        mockUK = new UserKeyword(mockUser, mockKeyword, mockPercent);
    }

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
        userKeywordRepository.deleteAll();
    }

    @Test
    @DisplayName("유저가 키워드의 퍼센트를 설정한다")
    public void save(){
        given(userKeywordRepository.save(any()))
                .willReturn(mockUK);
        given(userRepository.findById(any()))
                .willReturn(Optional.of(mockUser));
        given(keywordRepository.findById(any()))
                .willReturn(Optional.of(mockKeyword));
        UserKeyword saveUserKeyword = userKeywordService.save(mockUserId, mockKeywordId, mockPercent);
        assertEquals(saveUserKeyword, mockUK);
    }
}
