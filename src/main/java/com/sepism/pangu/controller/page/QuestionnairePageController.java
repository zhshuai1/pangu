package com.sepism.pangu.controller.page;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Log4j2
public class QuestionnairePageController {
    @RequestMapping(path = "/questionnairePage/{id}", method = RequestMethod.GET)
    public String getQuestionnairePage(@PathVariable String id, Model model) {
        log.info("user is accessing the questionnaire submission page.");
        model.addAttribute("id", id);
        return "questionnairePage";
    }

    public String submitQuestionnaire() {
        return null;
    }
}
