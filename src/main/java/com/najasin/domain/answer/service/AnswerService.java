package com.najasin.domain.answer.service;

import com.najasin.domain.answer.entity.Answer;
import com.najasin.domain.answer.entity.AnswerId;
import com.najasin.domain.answer.repository.AnswerRepository;
import com.najasin.domain.question.entity.Question;
import com.najasin.domain.question.repository.QuestionRepository;
import com.najasin.domain.user.entity.User;
import com.najasin.domain.user.repository.UserRepository;
import com.najasin.domain.userType.entity.UserType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public void deleteAnswers(String userId, UserType userType) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        List<Answer> answersToDelete = answerRepository.findByUser_Id(userId);
        List<Answer> answersLeft = new ArrayList<>();
        for (Answer answer : answersToDelete) {
            if (answer.getQuestion().getUserType() == userType) {
                answersLeft.add(answer);
            }
        }
        answersToDelete = answersToDelete.stream().filter(answer -> answer.getQuestion().getUserType()==userType).collect(Collectors.toList());
        user.updateAnswer(answersLeft);
        answerRepository.deleteAll(answersToDelete);
    }


}
