package com.sepism.pangu.validate;

import com.sepism.pangu.exception.InvalidInputException;
import com.sepism.pangu.model.register.RegisterRequest;
import org.apache.commons.lang.StringUtils;
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
        if ("phone".equals(type) && StringUtils.isBlank(validationCode)) {
            emptyFields.add("validationCode");
        }
        if (emptyFields.size() != 0) {
            throw new InvalidInputException(emptyFields.toString() + " should not be but are empty.");
        }
        validateAndNormalizeUsername(request);
    }

    private void validateAndNormalizeUsername(RegisterRequest request) throws InvalidInputException {
        String usernamePattern = "^[a-zA-Z0-9\\-_\\.@]{6,20}$";
        String passwordPattern = "^[a-zA-Z0-9\\-_\\.!@#$%^&*()]{6,20}$";
        switch (request.getType()) {
            case "phone":
                request.setUsername(request.getUsername().replaceAll("[^0-9]", ""));
                break;
            case "username":
            case "email":
                request.setUsername(request.getUsername().trim());
                if (!Pattern.matches(usernamePattern, request.getUsername())) {
                    throw new InvalidInputException("The username should only contain alphanumeric and [-_.@]");
                }
                break;
            default:
                throw new InvalidInputException("Invalid register type");
        }
        request.setValidationCode(request.getValidationCode().trim());
        if (!Pattern.matches(passwordPattern, request.getPassword())) {
            throw new InvalidInputException("The password should only contain alphanumeric and [-_.!@#$%^&*()]");
        }
    }
}
