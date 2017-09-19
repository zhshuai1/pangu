package com.sepism.pangu.model.repository;

import com.sepism.pangu.model.answer.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
}
