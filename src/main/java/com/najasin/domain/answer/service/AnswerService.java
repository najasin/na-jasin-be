package com.najasin.domain.answer.service;

import com.najasin.domain.answer.entity.Answer;
import com.najasin.domain.answer.repository.AnswerRepository;
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
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public Answer save(String userId, Long questionId, String answer) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Question question = questionRepository.findById(questionId).orElseThrow(EntityNotFoundException::new);
        return answerRepository.save(new Answer(user, question, answer));
    }


}
