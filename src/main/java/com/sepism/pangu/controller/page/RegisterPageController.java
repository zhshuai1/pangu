package com.sepism.pangu.controller.page;

import com.google.gson.Gson;
import com.sepism.pangu.handler.SepHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
public class RegisterPageController {

    private static final Gson GSON = new Gson();
    @Autowired
    private SepHandler registerHandler;

    @Autowired
    private SepHandler completeInformationHandler;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        log.info("User is accessing the register page.");
        return "register";
    }


    @RequestMapping(path = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String register(@RequestBody String formData) {
        log.info("User is registering with information: " + formData);
        return registerHandler.handle(formData).serialize();
    }

    @RequestMapping(path = "/complete-info", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String completeInfo(@RequestBody String formData) {
        log.info("User is completing registration with information: " + formData);
        return completeInformationHandler.handle(formData).serialize();
    }

}
