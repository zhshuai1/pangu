package com.sepism.pangu.model.repository;


import com.sepism.pangu.model.questionnaire.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
