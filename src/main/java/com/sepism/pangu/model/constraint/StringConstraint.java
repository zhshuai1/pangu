package com.sepism.pangu.model.constraint;

import com.sepism.pangu.exception.InvalidInputException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

@Getter
@Setter
public class StringConstraint extends DataConstraint {
    private String pattern;

    @Override
    protected void furtherValidate(String value) throws InvalidInputException {
        if (StringUtils.isBlank(pattern)) {
            // if the pattern is null, we think it is valid for any input.
            return;
        }
        if (!Pattern.matches(pattern, value)) {
            throw new InvalidInputException(String.format("The value [%s] should match the pattern [%s]",
                    value, pattern));
        }
    }
}
