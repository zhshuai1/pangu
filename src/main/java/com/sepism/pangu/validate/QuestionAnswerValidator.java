package com.sepism.pangu.validate;

import com.google.gson.Gson;
import com.sepism.pangu.exception.InvalidInputException;
import com.sepism.pangu.model.constraint.*;
import com.sepism.pangu.model.questionnaire.Question;
import com.sepism.pangu.model.repository.QuestionRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Log4j2
public class QuestionAnswerValidator {
    private static final Gson GSON = new Gson();
    @Autowired
    private QuestionRepository questionRepository;

    public void validate(long questionId, String answer) throws InvalidInputException {
        log.info("validating the question answer {} for questionId {}", answer, questionId);
        Question question = questionRepository.findOne(questionId);
        if (question == null) {
            log.error("did not find the questionId {} for the answer.", questionId);
        }
        String constraint = question.getConstraint();
        if (StringUtils.isBlank(constraint)) {
            return;
        }
        DataConstraint dataConstraint;
        switch (question.getType()) {
            case TEXT:
            case PHONE:
            case EMAIL:
                dataConstraint = GSON.fromJson(constraint, StringConstraint.class);
                break;
            case INTEGER:
                dataConstraint = GSON.fromJson(constraint, IntegerConstraint.class);
                break;
            case DOUBLE:
                dataConstraint = GSON.fromJson(constraint, DoubleConstraint.class);
                break;
            case DATE:
                dataConstraint = GSON.fromJson(constraint, DateConstraint.class);
                break;
            case RADIO:
            case CHECKBOX:
            case SELECT:
            case MULTISELECT:
                dataConstraint = GSON.fromJson(constraint, ChoicesConstraint.class);
                break;

            // For default case, we do not do any validation;
            default:
                return;
        }
        dataConstraint.validate(answer);
    }
}
