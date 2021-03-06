package com.sepism.pangu.controller.data;

import com.sepism.pangu.constant.ErrorCode;
import com.sepism.pangu.external.SmsSender;
import com.sepism.pangu.model.authentication.ValidationCode;
import com.sepism.pangu.model.handler.Response;
import com.sepism.pangu.model.repository.UserRepository;
import com.sepism.pangu.model.repository.ValidationCodeRepository;
import com.sepism.pangu.model.user.User;
import com.sepism.pangu.util.DataNormalizer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@Log4j2
public class ValidationCodeController {
    @Autowired
    private ValidationCodeRepository validationCodeRepository;

    @Autowired
    private SmsSender smsSender;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/validationCode", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getValidationCode(@RequestParam String phone) {
        log.info("Generating a validation code for phone {}", phone);
        String validationCode = String.format("%06d", (int) (Math.random() * 1000000));
        log.info("The validation code is {}.", validationCode);
        phone = DataNormalizer.normalizePhone(phone);

        try {
            List<User> existedUsers = userRepository.findByNickNameOrEmailOrPhoneNumber(phone, phone, phone);
            if (existedUsers.size() != 0) {
                log.warn("The phone number has been registered in sep.");
                return new Response(ErrorCode.USER_EXIST).serialize();
            }

            Response response = smsSender.sendSms(phone, validationCode, SmsSender.SmsUsage.REGISTER);
            if(ErrorCode.SUCCESS.equals(response.getErrorCode())){
                ValidationCode validation = ValidationCode.builder().code(validationCode).phone(phone).lastUpdateTime(new
                        Date()).build();
                validationCodeRepository.save(validation);
                log.info("Store validation code and send Sms to user successfully.");
            }else{
                log.error("Failed to send sms to user or save validation code to db.");
            }
            return response.serialize();
        } catch (Exception e) {
            log.error("Failed to store the validation code in db.", e);
            return new Response(ErrorCode.INTERNAL_ERROR).serialize();
        }
    }
}
