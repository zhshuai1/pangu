package com.sepism.pangu.validate;

import com.google.gson.Gson;
import com.sepism.pangu.exception.InvalidInputException;
import com.sepism.pangu.model.answer.QuestionAnswer;
import com.sepism.pangu.model.constraint.*;
import com.sepism.pangu.model.questionnaire.Question;
import org.apache.commons.lang.StringUtils;

public final class QuestionAnswerValidator {
    private static final Gson GSON = new Gson();

    public static void validate(Question question, QuestionAnswer answer) throws InvalidInputException {
        String constraint = question.getConstraint();
        if (StringUtils.isBlank(constraint)) {
            return;
        }
        DataConstraint dataConstraint;
        switch (question.getType()) {
            case TEXT:
            case STRING:
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

            // For default case, we do not do any validation;
            default:
                return;
        }
        dataConstraint.validate(answer.getAnswer());
    }
}
