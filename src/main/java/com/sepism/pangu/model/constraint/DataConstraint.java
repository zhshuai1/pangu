package com.sepism.pangu.model.constraint;

import com.sepism.pangu.exception.InvalidInputException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

@Getter
@Setter
public class DataConstraint {
    private boolean required;

    public DataConstraint() {
        required = true;
    }

    public void validate(String value) throws InvalidInputException {
        if (required && StringUtils.isBlank(value)) {
            throw new InvalidInputException("The field is required, but is empty.");
        }
    }
}
