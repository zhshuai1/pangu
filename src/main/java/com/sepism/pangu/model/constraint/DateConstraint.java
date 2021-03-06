package com.sepism.pangu.model.constraint;

import com.sepism.pangu.exception.InvalidInputException;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DateConstraint extends DataConstraint {

    @Override
    protected void furtherValidate(String value) throws InvalidInputException {
        try {
            new Date(Long.valueOf(value));
        } catch (Exception e) {
            throw new InvalidInputException(String.format("Failed to convert the value [%s] to date", value), e);
        }
    }
}
