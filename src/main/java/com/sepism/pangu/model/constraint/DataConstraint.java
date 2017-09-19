package com.sepism.pangu.model.constraint;

import com.sepism.pangu.exception.InvalidInputException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@Log4j2
public class DataConstraint {
    private boolean required;

    public DataConstraint() {
        required = true;
    }

    public void validate(String value) throws InvalidInputException {
        if (required && StringUtils.isBlank(value)) {
            throw new InvalidInputException("The field is required, but is empty.");
        }
        if (!required && StringUtils.isBlank(value)) {
            return;
        }
        furtherValidate(value);
    }

    protected void furtherValidate(String value) throws InvalidInputException {
        return;
    }
}
