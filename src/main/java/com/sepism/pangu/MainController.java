package com.sepism.pangu;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import lombok.Setter;


@Setter
@Controller
@SessionAttributes("merchantId")
@Log4j2
public class MainController {

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public String sayHello2(@RequestParam("name") String id, Model model) {
        log.info("The value of name is {}\n\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++" +
                "++++++++++++++",id);
        model.addAttribute("url", "world");
        return "test";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login( Model model) {
        return "login";
    }
}

