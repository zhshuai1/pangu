package com.sepism.pangu.model.repository;

import com.sepism.pangu.model.questionnaire.Questionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long>,
        PagingAndSortingRepository<Questionnaire, Long> {
    //TODO: since the query has so many restrictions, performance is an issue?
    Page<Questionnaire> findAllByHotGreaterThanEqualOrderByHot(long hot, Pageable pageable);

    List<Questionnaire> findByIdIn(List<Long> questionnaireIds);
}
