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
    private double min;
    private double max;

    @Override
    public void validate(String value) throws InvalidInputException {
        super.validate(value);
        double doubleValue;
        try {
            doubleValue = Long.valueOf(value);
        } catch (Exception e) {
            throw new InvalidInputException(String.format("Failed to convert String [%s] to Double", value), e);
        }
        if (doubleValue < min || doubleValue > max) {
            throw new InvalidInputException(String.format("The value %f is lower than min %f or greater than max %f",
                    doubleValue, min, max));
        }

    }
}
