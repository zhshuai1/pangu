package com.sepism.pangu.model.repository;

import com.sepism.pangu.model.questionnaire.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
}
