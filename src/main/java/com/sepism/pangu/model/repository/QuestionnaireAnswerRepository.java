package com.sepism.pangu.model.repository;

import com.sepism.pangu.model.answer.QuestionnaireAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface QuestionnaireAnswerRepository extends JpaRepository<QuestionnaireAnswer, Long> {
    List<QuestionnaireAnswer> findByQuestionnaireIdAndUserIdAndCurrent(long questionnaireId, long userId,
                                                                       boolean current);
}
