package com.sepism.pangu.model.constraint;

import com.sepism.pangu.exception.InvalidInputException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntegerConstraint extends DataConstraint {
    private long min;
    private long max;

    public IntegerConstraint() {
        min = Long.MIN_VALUE;
        max = Long.MAX_VALUE;
    }

    @Override
    public void validate(String value) throws InvalidInputException {
        super.validate(value);
        long longValue;
        try {
            longValue = Long.valueOf(value);
        } catch (Exception e) {
            throw new InvalidInputException(String.format("Failed to convert String [%s] to Long", value), e);
        }
        if (longValue < min || longValue > max) {
            throw new InvalidInputException(String.format("The value %d is lower than min %d or greater than max %d",
                    longValue, min, max));
        }
    }
}
