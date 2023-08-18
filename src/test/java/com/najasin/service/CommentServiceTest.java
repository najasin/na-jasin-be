package com.najasin.service;

import com.najasin.domain.comment.entity.Comment;
import com.najasin.domain.comment.repository.CommentRepository;
import com.najasin.domain.comment.service.CommentService;
import com.najasin.domain.keyword.entity.Keyword;
import com.najasin.domain.question.entity.Question;
import com.najasin.domain.question.entity.QuestionType;
import com.najasin.domain.question.repository.QuestionRepository;
import com.najasin.domain.user.entity.Oauth2Entity;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.entity.enums.Provider;
import com.najasin.domain.user.entity.enums.Role;
import com.najasin.domain.user.repository.UserRepository;
import com.najasin.domain.userKeyword.entity.UserKeyword;
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
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private QuestionRepository questionRepository;
    private String mockUserId;
    private Oauth2Entity mockOauth2Entity;
    private User mockUser;
    private Question mockQuestion;
    private Long mockQuestionId;
    private Comment mockComment;
    private String mockCommentContent;
    private String mockCommentNickname;

    @BeforeEach
    public void setup() {
        mockUserId = "test-uuid";
        mockOauth2Entity = Oauth2Entity.builder()
                .provider(Provider.KAKAO)
                .providerId("test-provider-id")
                .providerUsername("test-provider-nickname")
                .email("test@kakao.com")
                .build();
        mockUser = User.builder()
                .id(mockUserId)
                .role( new ArrayList<>(List.of(Role.ROLE_MEMBER)))
                .comments(new ArrayList<>())
                .oauth2Entity(mockOauth2Entity)
                .build();
        mockQuestionId = 123456789L;
        mockQuestion = new Question(mockQuestionId, "질문", QuestionType.FOR_OTHERS, new ArrayList<>(), new UserType(1L, "JFF", null));
        mockCommentContent = "테스트 코멘트";
        mockCommentNickname = "테스트 닉네임";
        mockComment = Comment.builder()
                .user(mockUser)
                .question(mockQuestion)
                .comment(mockCommentContent)
                .nickname(mockCommentNickname)
                .build();
    }

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
        questionRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    @DisplayName("타적나사를 저장한다")
    public void save() {
        //given
        given(userRepository.findById(any()))
                .willReturn(Optional.of(mockUser));
        given(questionRepository.findById(any()))
                .willReturn(Optional.of(mockQuestion));
        given(commentRepository.save(any()))
                .willReturn(mockComment);
        //when
        Comment comment = commentService.save(mockUserId, mockQuestionId, mockCommentNickname, mockCommentContent);
        //then
        assertEquals(mockComment.getComment(), comment.getComment());

        verify(userRepository, times(1)).findById(mockUserId);
        verify(questionRepository, times(1)).findById(mockQuestionId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("타적나사를 삭제한다")
    public void delete(){
        //given
        given(userRepository.findById(any()))
                .willReturn(Optional.of(mockUser));
        given(questionRepository.findById(any()))
                .willReturn(Optional.of(mockQuestion));
        given(commentRepository.findById(any()))
                .willReturn(Optional.of(mockComment));
        mockUser.getComments().add(mockComment);
        mockQuestion.getComments().add(mockComment);
        //then
        assertDoesNotThrow(() -> commentService.delete(mockUserId, mockQuestionId));
        assertEquals(true, mockUser.getComments().isEmpty());
        assertEquals(true, mockQuestion.getComments().isEmpty());
    }
}
