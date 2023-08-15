package com.najasin.domain.comment.service;

import com.najasin.domain.comment.entity.Comment;
import com.najasin.domain.comment.entity.CommentId;
import com.najasin.domain.comment.repository.CommentRepository;
import com.najasin.domain.question.entity.Question;
import com.najasin.domain.question.repository.QuestionRepository;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment save(String userId, Long questionId, String nickname, String comment) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Question question = questionRepository.findById(questionId).orElseThrow(EntityNotFoundException::new);
        Comment newComment = new Comment(user, question, comment, nickname);
        return commentRepository.save(newComment);
    }

    @Transactional
    public boolean delete(String userId, Long questionId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Question question = questionRepository.findById(questionId).orElseThrow(EntityNotFoundException::new);
        try {
            commentRepository.deleteById(new CommentId(user, question));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
