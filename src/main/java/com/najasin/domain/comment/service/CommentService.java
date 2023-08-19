package com.najasin.domain.comment.service;

import com.najasin.domain.comment.entity.Comment;
import com.najasin.domain.comment.entity.CommentId;
import com.najasin.domain.comment.repository.CommentRepository;
import com.najasin.domain.question.entity.Question;
import com.najasin.domain.question.repository.QuestionRepository;
import com.najasin.domain.user.dto.Page;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.repository.UserRepository;
import com.najasin.domain.userType.entity.UserType;
import com.najasin.domain.userType.repository.UserTypeRepository;
import com.najasin.global.audit.AuditEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final UserTypeRepository userTypeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment save(String userId, Long questionId, String nickname, String comment) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Question question = questionRepository.findById(questionId).orElseThrow(EntityNotFoundException::new);
        Comment newComment = Comment.builder()
                .user(user)
                .question(question)
                .comment(comment)
                .nickname(nickname)
                .auditEntity(new AuditEntity())
                .build();

        commentRepository.save(newComment);
        user.getComments().add(newComment);
        question.getComments().add(newComment);
        return newComment;
    }

    @Transactional
    public boolean delete(String userId, Long questionId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Question question = questionRepository.findById(questionId).orElseThrow(EntityNotFoundException::new);
        Comment comment = commentRepository.findById(new CommentId(user, question)).orElseThrow(EntityNotFoundException::new);
        user.getComments().remove(comment);
        question.getComments().remove(comment);
        commentRepository.delete(comment);
        return true;
    }


}
