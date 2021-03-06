package com.sepism.pangu.model.constraint;

import com.sepism.pangu.exception.InvalidInputException;

import java.util.regex.Pattern;

public class PatternConstraint extends DataConstraint {

    @Override
    protected void furtherValidate(String value) throws InvalidInputException {
        try {
            Pattern.compile(value);
        } catch (Exception e) {
            throw new InvalidInputException(String.format("Failed to convert the value [%s] to pattern", value), e);
        }
    }
}
