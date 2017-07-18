package com.sepism.pangu.model.constraint;

import com.sepism.pangu.exception.InvalidInputException;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
public class StringConstraint extends DataConstraint {
    private String pattern;

    @Override
    public void validate(String value) throws InvalidInputException {
        super.validate(value);
        if (!Pattern.matches(pattern, value)) {
            throw new InvalidInputException(String.format("The value [%s] should match the pattern [%s]",
                    value, pattern));
        }
    }
}
