package com.sepism.pangu.model.repository;

import com.sepism.pangu.model.questionnaire.Questionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long>,
        PagingAndSortingRepository<Questionnaire, Long> {
    //TODO: since the query has so many restrictions, performance is an issue?
    Page<Questionnaire> findAllByHotGreaterThanEqualOrderByHot(long hot, Pageable pageable);

    List<Questionnaire> findByIdIn(List<Long> questionnaireIds);

    @Query("select qn.id, qn.titleCn from Questionnaire qn where qn.id in ?1")
    List<Object[]> findIdTitleCnByIdIn(List<Long> questionnaireIds);

    // The return result in fact is List<Object[]>, there is no compile/runtime error if return List<Questionnaire>.
    // Since at compilation, the compiler does not know the exact type; at runtime, there will be type erasure.
    @Query("select qn.id, qn.cover from Questionnaire qn where qn.id in ?1")
    List<Object[]> findIdCoverByIdIn(List<Long> questionnaireIds);
}
