package com.sepism.pangu.model.constraint;

import com.sepism.pangu.exception.InvalidInputException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DoubleConstraint extends DataConstraint {
    private Double min;
    private Double max;

    @Override
    protected void furtherValidate(String value) throws InvalidInputException {
        double doubleValue;
        try {
            doubleValue = Long.valueOf(value);
        } catch (Exception e) {
            throw new InvalidInputException(String.format("Failed to convert String [%s] to Double", value), e);
        }
        if (min != null && doubleValue < min) {
            throw new InvalidInputException(String.format("The value %f is lower than min %f ", doubleValue, min));
        }
        if (max != null && doubleValue > max) {
            throw new InvalidInputException(String.format("The value %f is greater than max %f", doubleValue, max));
        }
    }
}
