package com.sepism.pangu.controller;

import com.google.gson.Gson;
import com.sepism.pangu.handler.SepHandler;
import com.sepism.pangu.model.register.RegisterRequest;
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
public class RegisterController {

    @Autowired
    private SepHandler registerHandler;

    private static final Gson GSON = new Gson();

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "register";
    }


    @RequestMapping(path = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String register(@RequestBody String formData) {
        log.info("User is registering with information: " + formData);
        RegisterRequest request = GSON.fromJson(formData, RegisterRequest.class);
        return registerHandler.handle(request).serialize();
    }

}
