package com.sepism.pangu.model.constraint;

import com.sepism.pangu.exception.InvalidInputException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntegerConstraint extends DataConstraint {
    private Long min;
    private Long max;

    public IntegerConstraint() {
        min = Long.MIN_VALUE;
        max = Long.MAX_VALUE;
    }

    @Override
    protected void furtherValidate(String value) throws InvalidInputException {
        long longValue;
        try {
            longValue = Long.valueOf(value);
        } catch (Exception e) {
            throw new InvalidInputException(String.format("Failed to convert String [%s] to Long", value), e);
        }
        if (min != null && longValue < min) {
            throw new InvalidInputException(String.format("The value %d is lower than min %d ", longValue, min));
        }
        if (max != null && longValue > max) {
            throw new InvalidInputException(String.format("The value %d is greater than max %d", longValue, max));
        }
    }
}

