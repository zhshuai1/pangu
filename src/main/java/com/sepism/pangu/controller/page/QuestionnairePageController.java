package com.sepism.pangu.controller.page;


import com.sepism.pangu.constant.RequestAttribute;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

@Controller
@Log4j2
public class QuestionnairePageController {
    @RequestMapping(path = "/questionnairePage/{id}", method = RequestMethod.GET)
    public String getQuestionnairePage(@PathVariable String id, HttpServletRequest request, Model model) {
        log.info("user is accessing the questionnaire submission page.");
        if (!(boolean) request.getAttribute(RequestAttribute.LOGGED_IN)) {
            log.info("The user did not login yet, will redirect to login page");
            return "redirect:/login?redirectUrl=" + URLEncoder.encode("/questionnairePage/" + id);
        }
        model.addAttribute("id", id);
        return "questionnairePage";
    }

    public String submitQuestionnaire() {
        return null;
    }
}
