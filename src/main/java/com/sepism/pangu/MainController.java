package com.sepism.pangu;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.EventLogger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.message.StructuredDataMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.UUID;


@Setter
@Controller
@SessionAttributes("merchantId")
@Log4j2
public class MainController {

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public String sayHello2(@RequestParam("name") String id, Model model) {
        log.error("The value of name is {}", id);
        model.addAttribute("url", "world");
        myTest("Aloha!!!");
        return "test";
    }

    private void myTest(String testStr) {
        log.trace(testStr);
        EventLogger.logEvent(new StructuredDataMessage(UUID.randomUUID().toString().replaceAll("\\-", ""), testStr, "TYPE"));
        log.debug("This is a debug message");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }
}

