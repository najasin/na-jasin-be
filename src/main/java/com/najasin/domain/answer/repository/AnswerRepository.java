package com.najasin.domain.answer.repository;

import com.najasin.domain.answer.entity.Answer;
import com.najasin.domain.answer.entity.AnswerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, AnswerId> {
    List<Answer> findByUser_Id(String userId);
}
