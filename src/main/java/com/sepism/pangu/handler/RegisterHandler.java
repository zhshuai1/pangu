package com.sepism.pangu.handler;


import com.google.gson.Gson;
import com.sepism.pangu.constant.ErrorCode;
import com.sepism.pangu.constant.GlobalConstant;
import com.sepism.pangu.constant.UsernameType;
import com.sepism.pangu.exception.InternalException;
import com.sepism.pangu.exception.InvalidInputException;
import com.sepism.pangu.model.authentication.Session;
import com.sepism.pangu.model.authentication.ValidationCode;
import com.sepism.pangu.model.handler.Request;
import com.sepism.pangu.model.handler.Response;
import com.sepism.pangu.model.register.RegisterRequest;
import com.sepism.pangu.model.repository.SessionRepository;
import com.sepism.pangu.model.repository.UserRepository;
import com.sepism.pangu.model.repository.ValidationCodeRepository;
import com.sepism.pangu.model.user.User;
import com.sepism.pangu.util.DateUtil;
import com.sepism.pangu.validate.RegisterRequestValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class RegisterHandler extends SepHandler {
    private static final Gson GSON = new Gson();

    @Autowired
    private RegisterRequestValidator registerRequestValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationCodeRepository validationCodeRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Response process(Request request) throws InvalidInputException, InternalException {
        RegisterRequest registerRequest = (RegisterRequest) request;
        registerRequestValidator.validateAndNormalize(registerRequest);
        log.info("The request after validate and normalize is: [{}]", GSON.toJson(registerRequest));
        //username has been validated in the validator, so no need to check the nullability.
        String username = registerRequest.getUsername();
        List<User> usersExisted = userRepository.findByNickNameOrEmailOrPhoneNumber(username, username, username);
        if (usersExisted.size() != 0) {
            return new Response(ErrorCode.USER_EXIST, "username");
        }
        String type = registerRequest.getType();
        String password = registerRequest.getPassword();
        String validationCode = registerRequest.getValidationCode();
        User user = User.builder().nickName(username).password(password).build();
        switch (type) {
            case UsernameType.EMAIL:
                user.setEmail(username);
                break;
            case UsernameType.PHONE:
                ValidationCode validationCodeRecord = validationCodeRepository.findOne(username);
                log.info("The validationRecord retrieved from db is: [{}]", GSON.toJson(validationCodeRecord));
                if (validationCodeRecord == null
                        || !validationCodeRecord.getCode().equals(validationCode)
                        || (DateUtil.diff(new Date(), validationCodeRecord.getLastUpdateTime())
                        > GlobalConstant.VALIDATIONCODE_EXPIRED_TIME)) {
                    throw new InvalidInputException("The validation code is not valid");
                }
                user.setPhoneNumber(username);
                break;
            case UsernameType.USERNAME:
                break;
            default:
                throw new InvalidInputException("The username type is invalid.");
        }

        userRepository.save(user);
        // This token will pass back to front end, and used to verify the user identity. Since at this time, the user
        // did not log in yet.
        String token = UUID.randomUUID().toString();
        Session session = Session.builder().id(user.getId()).token(token).lastAccessTime(new Date()).build();
        sessionRepository.save(session);
        return new Response(ErrorCode.SUCCESS, token);
    }
}
