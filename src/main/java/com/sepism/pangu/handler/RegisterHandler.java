package com.sepism.pangu.handler;


import com.sepism.pangu.constant.ErrorCode;
import com.sepism.pangu.exception.InternalException;
import com.sepism.pangu.exception.InvalidInputException;
import com.sepism.pangu.model.handler.Request;
import com.sepism.pangu.model.handler.Response;
import com.sepism.pangu.model.register.RegisterRequest;
import com.sepism.pangu.model.repository.UserRepository;
import com.sepism.pangu.model.user.User;
import com.sepism.pangu.validate.RegisterRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegisterHandler extends SepHandler {
    @Autowired
    private RegisterRequestValidator registerRequestValidator;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Response process(Request request) throws InvalidInputException, InternalException {
        RegisterRequest registerRequest = (RegisterRequest) request;
        registerRequestValidator.validateAndNormalize(registerRequest);
        //username has been validated in the validator, so no need to check the nullability.
        String username = registerRequest.getUsername();
        List<User> usersExisted = userRepository.findByNickNameOrEmailOrPhoneNumber(username, username, username);
        if (usersExisted.size() != 0) {
            return new Response(ErrorCode.USER_EXIST, "username");
        }
        return new Response(ErrorCode.SUCCESS);
    }
}
