package com.sepism.pangu.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
public class HealthCheckController {
    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    @ResponseBody
    public String ping() {
        log.info("I'm happy. Because this is my character setting.");
        return "Sepism is healthy.";
    }
}
