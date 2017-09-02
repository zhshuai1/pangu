package com.sepism.pangu.controller.page;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Log4j2
public class IndexPageController {
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index() {
        return viewIndex();
    }

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String viewIndex() {
        log.info("User is accessing the index page.");
        return "index";
    }

    @RequestMapping(path = "/viewQuestionnaires", method = RequestMethod.GET)
    public String viewQuestionnaires() {
        return "viewQuestionnaires";
    }

    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public String account() {
        return "account";
    }
}
