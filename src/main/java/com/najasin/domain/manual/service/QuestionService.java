package com.najasin.domain.manual.service;

import static com.najasin.domain.manual.entity.question.QuestionType.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.najasin.domain.manual.dto.param.JffMyQuestion;
import com.najasin.domain.manual.entity.question.Question;
import com.najasin.domain.manual.repository.QuestionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class QuestionService {
	private final QuestionRepository questionRepository;

	@Transactional(readOnly = true)
	public List<JffMyQuestion> findAll(String userTypeName) {
		List<Question> questionList = questionRepository.findAllByQuestionTypeAndUserTypeName(FOR_USER, userTypeName);

		return questionList.stream().map(Question::toJffMyQuestion).toList();
	}

	@Transactional(readOnly = true)
	public List<Question> findAllByIdList(List<Long> questionIds) {
		return questionIds.stream().map(this::findById).toList();
	}

	@Transactional(readOnly = true)
	public Question findById(Long id) {
		return questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
}
