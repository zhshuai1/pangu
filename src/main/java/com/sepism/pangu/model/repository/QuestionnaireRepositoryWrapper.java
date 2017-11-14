package com.sepism.pangu.model.repository;

import com.sepism.pangu.model.questionnaire.Question;
import com.sepism.pangu.model.questionnaire.Questionnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionnaireRepositoryWrapper {
    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    public List<Questionnaire> findIdTitleCnByIdIn(List<Long> questionnaireIds) {
        List<Object[]> objects = questionnaireRepository.findIdTitleCnByIdIn(questionnaireIds);
        return objects.stream().map(o -> Questionnaire.builder().id((long) o[0]).titleCn((String) o[1]).build()).collect
                (Collectors.toList());
    }

    public List<Questionnaire> findIdCoverByIdIn(List<Long> questionnaireIds) {
        List<Object[]> objects = questionnaireRepository.findIdCoverByIdIn(questionnaireIds);
        return objects.stream().map(o -> Questionnaire.builder().id((long) o[0]).cover((Question) o[1]).build()).collect
                (Collectors.toList());
    }

}
