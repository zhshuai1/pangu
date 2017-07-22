package com.sepism.pangu.model.repository;

import com.sepism.pangu.model.questionnaire.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    List<Choice> findByParent(long parentId);
}
