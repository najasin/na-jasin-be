package com.najasin.service;

import com.najasin.domain.keyword.entity.Keyword;
import com.najasin.domain.keyword.repository.KeywordRepository;
import com.najasin.domain.user.entity.Oauth2Entity;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.entity.enums.Provider;
import com.najasin.domain.user.entity.enums.Role;
import com.najasin.domain.user.repository.UserRepository;
import com.najasin.domain.userKeyword.entity.UserKeyword;
import com.najasin.domain.userKeyword.repository.UserKeywordRepository;
import com.najasin.domain.userKeyword.service.UserKeywordService;
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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


import java.util.*;

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

    private UserKeyword prevUserKeyword;
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
        mockKeywordId = 123456789L;
        mockKeyword = new Keyword(mockKeywordId, "키워드");
        mockPercent = 50;
        mockUK = new UserKeyword(mockUser, mockKeyword, mockPercent);
        prevUserKeyword = new UserKeyword(mockUser, mockKeyword, 50, 30, 5);
        List<UserKeyword> list = new ArrayList<>();
        list.add(prevUserKeyword);
        mockUser = User.builder()
                .id(mockUserId)
                .role( new ArrayList<>(List.of(Role.ROLE_MEMBER)))
                .userKeywords(list)
                .oauth2Entity(mockOauth2Entity)
                .build();
    }

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
        userKeywordRepository.deleteAll();
    }

    @Test
    @DisplayName("유저가 키워드의 퍼센트를 설정한다")
    public void save() {
        given(userKeywordRepository.save(any()))
                .willReturn(mockUK);
        given(userRepository.findById(any()))
                .willReturn(Optional.of(mockUser));
        given(keywordRepository.findById(any()))
                .willReturn(Optional.of(mockKeyword));
        UserKeyword saveUserKeyword = userKeywordService.save(mockUserId, mockKeywordId, mockPercent);
        assertEquals(saveUserKeyword, mockUK);
    }

    @Test
    @DisplayName("유저의 원래 키워드 퍼센트를 가져온다")
    public void getOriginKeywordPercents() {
        //given
        given(userRepository.findById(any())).willReturn(Optional.of(mockUser));
        //when
        Map<String, Integer> getPercents = userKeywordService.getOriginKeywordPercents(mockUserId);
        //then
        for (UserKeyword userKeyword : mockUser.getUserKeywords()) {
            String keyword = userKeyword.getKeyword().getName();
            assertEquals(Optional.ofNullable(getPercents.get(keyword)), Optional.ofNullable(userKeyword.getOriginPercent()));
        }
    }

    @Test
    @DisplayName("유저의 합산 키워드 퍼센트를 가져온다")
    public void getOtherKeywordPercents() {
        //given
        given(userRepository.findById(any())).willReturn(Optional.of(mockUser));
        //when
        Map<String, Long> othersPercent = userKeywordService.getOtherKeywordPercents(mockUserId);
        //then
        for (UserKeyword userKeyword : mockUser.getUserKeywords()) {
            String keyword = userKeyword.getKeyword().getName();
            assertEquals(Optional.ofNullable(othersPercent.get(keyword)), Optional.ofNullable(Long.valueOf(userKeyword.getOriginPercent()+userKeyword.getOthersPercent())/(userKeyword.getOthersCount()+1)));
        }
    }


    @Test
    @DisplayName("타인이 유저 키워드 퍼센트 설정에 기여한다")
    public void updateByOthers() {

        // Given
        String userId = mockUserId;
        Long keywordId = mockKeywordId;
        int percentToAdd = 10;

        User user = mockUser;
        Keyword keyword = mockKeyword;


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(keywordRepository.findById(keywordId)).thenReturn(Optional.of(keyword));
        when(userKeywordRepository.save(any(UserKeyword.class))).thenReturn(prevUserKeyword);

        // When
        UserKeyword updatedUserKeyword = userKeywordService.updateByOthers(userId, keyword.getName(), percentToAdd);

        // Then
//        assertEquals(prevUserKeyword.getOthersPercent() + percentToAdd, updatedUserKeyword.getOthersPercent());
//        assertEquals(prevUserKeyword.getOthersCount() + 1, updatedUserKeyword.getOthersCount());

        verify(userRepository, times(1)).findById(userId);
        verify(keywordRepository, times(1)).findById(keywordId);
        verify(userKeywordRepository, times(1)).save(any(UserKeyword.class));
    }

    @Test
    @DisplayName("유저가 원래 퍼센트를 수정한다")
    public void updateByUser() {
        //given
        Map<String, Integer> dto = new HashMap<>();
        dto.put("키워드", 10);
        given(userRepository.findById(mockUserId)).willReturn(Optional.of(mockUser));
        given(keywordRepository.findKeywordByName("키워드")).willReturn(new Keyword(1L, "키워드"));
        //when
        userKeywordService.updateByUser(mockUserId, dto);
        //then
        for (UserKeyword userKeyword : mockUser.getUserKeywords()) {
            assertEquals(Optional.ofNullable(userKeyword.getOriginPercent()), Optional.ofNullable(dto.get(userKeyword.getKeyword().getName())));
        }

    }
}
