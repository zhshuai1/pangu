package com.sepism.pangu.validate;

import com.sepism.pangu.constant.RegularExpression;
import com.sepism.pangu.constant.UsernameType;
import com.sepism.pangu.exception.InvalidInputException;
import com.sepism.pangu.model.handler.RegisterRequest;
import com.sepism.pangu.util.DataNormalizer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class RegisterRequestValidator {
    public void validateAndNormalize(RegisterRequest request) throws InvalidInputException {
        String type = request.getType();
        String username = request.getUsername();
        String validationCode = request.getValidationCode();
        String password = request.getPassword();
        List<String> emptyFields = new ArrayList<>();
        if (StringUtils.isBlank(type)) {
            emptyFields.add("type");
        }
        if (StringUtils.isBlank(username)) {
            emptyFields.add("username");
        }
        if (StringUtils.isBlank(password)) {
            emptyFields.add("password");
        }
        if (UsernameType.PHONE.equals(type) && StringUtils.isBlank(validationCode)) {
            emptyFields.add("validationCode");
        }
        if (emptyFields.size() != 0) {
            throw new InvalidInputException(emptyFields.toString() + " should not be but are empty.");
        }
        validateAndNormalizeUsername(request);
    }

    private void validateAndNormalizeUsername(RegisterRequest request) throws InvalidInputException {
        String usernamePattern = RegularExpression.USERNAME_PATTERN;
        String passwordPattern = RegularExpression.PASSWORD_PATTERN;
        switch (request.getType()) {
            case UsernameType.PHONE:
                request.setUsername(DataNormalizer.normalizePhone(request.getUsername()));
                request.setValidationCode(request.getValidationCode().trim());
                break;
            case UsernameType.USERNAME:
            case UsernameType.EMAIL:
                request.setUsername(request.getUsername().trim());
                if (!Pattern.matches(usernamePattern, request.getUsername())) {
                    throw new InvalidInputException("The username should only contain alphanumeric and [-_.@]");
                }
                break;
            default:
                throw new InvalidInputException("Invalid register type");
        }

        if (!Pattern.matches(passwordPattern, request.getPassword())) {
            throw new InvalidInputException("The password should only contain alphanumeric and [-_.!@#$%^&*()]");
        }
    }
}
