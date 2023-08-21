package com.najasin.domain.manual.repository;

import com.najasin.domain.manual.entity.answer.Answer;
import com.najasin.domain.manual.entity.answer.AnswerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, AnswerId> {

    List<Answer> findByUserId(String userId);
}
