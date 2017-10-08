package com.sepism.pangu.handler;


import com.google.gson.Gson;
import com.sepism.pangu.constant.ErrorCode;
import com.sepism.pangu.exception.InternalException;
import com.sepism.pangu.exception.InvalidInputException;
import com.sepism.pangu.model.authentication.Session;
import com.sepism.pangu.model.handler.CompletionResponse;
import com.sepism.pangu.model.handler.Response;
import com.sepism.pangu.model.repository.SessionRepositoryRedis;
import com.sepism.pangu.model.repository.UserRepository;
import com.sepism.pangu.model.user.User;
import com.sepism.pangu.validate.QuestionAnswerValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class CompleteInformationHandler extends SepHandler {
    private static final Gson GSON = new Gson();

    private static final String USERNAME = "username";
    private static final String TOKEN = "token";

    @Autowired
    private QuestionAnswerValidator questionAnswerValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepositoryRedis sessionRepository;

    @Override
    protected Response process(String data) throws InvalidInputException, InternalException {
        Map<String, String> infoMap = GSON.fromJson(data, Map.class);
        long userId = 0;
        try {
            userId = Long.parseLong(infoMap.get(USERNAME));
        } catch (Exception e) {
            throw new InvalidInputException("Failed to parse userId to long", e);
        }
        String token = infoMap.get(TOKEN);
        infoMap.remove(USERNAME);
        infoMap.remove(TOKEN);
        Session session = sessionRepository.findOne(userId);
        log.info("The session stored in the database is {}", GSON.toJson(session));
        if (token == null || session == null || !token.equals(session.getToken())) {
            log.warn("The token from client is different from the one stored in db.");
            throw new InvalidInputException("The request is invalid, because the userId/token is not correct.");
        }
        for (Map.Entry<String, String> entry : infoMap.entrySet()) {
            long questionId = Long.valueOf(entry.getKey());
            String answer = entry.getValue();
            questionAnswerValidator.validate(questionId, answer);
        }

        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new InvalidInputException("The user is not registered yet");
        }
        Date birthday = new Date(Long.valueOf(infoMap.get(AttributeMapping.BIRTHDAY)));
        long originAddress = Long.valueOf(infoMap.get(AttributeMapping.ORIGIN_ADDRESS));
        long currentAddress = Long.valueOf(infoMap.get(AttributeMapping.CURRENT_ADDRESS));
        String phone = infoMap.get(AttributeMapping.PHONE);
        String email = infoMap.get(AttributeMapping.EMAIL);
        String fullName = infoMap.get(AttributeMapping.FULLNAME);
        User.Gender gender = AttributeMapping.genderMapping.get(infoMap.get(AttributeMapping.GENDER));
        log.info("Have prepared all attributes ready for the user.");
        user.setBirthDay(birthday);
        user.setOriginAddress(originAddress);
        user.setCurrentAddress(currentAddress);
        user.setPhoneNumber(phone);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setGender(gender);
        user.setCompleted(true);
        userRepository.save(user);
        log.info("Update the user information successfully.");
        return new CompletionResponse(ErrorCode.SUCCESS, "OK");
    }

    private static final class AttributeMapping {
        public static final String FULLNAME = "3";
        public static final String PHONE = "6";
        public static final String EMAIL = "7";
        public static final String ORIGIN_ADDRESS = "8";
        public static final String CURRENT_ADDRESS = "9";
        public static final String BIRTHDAY = "4";
        public static final String GENDER = "5";
        private static final String MALE = "3";
        private static final String FEMALE = "4";
        public static final Map<String, User.Gender> genderMapping = new HashMap<String, User.Gender>() {{
            put(MALE, User.Gender.MALE);
            put(FEMALE, User.Gender.FEMALE);
        }};
    }
}
