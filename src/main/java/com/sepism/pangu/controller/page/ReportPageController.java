package com.sepism.pangu.controller.page;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Log4j2
public class ReportPageController {
    @RequestMapping(path = "/reports/{id}", method = RequestMethod.GET)
    public String getReportPage(@PathVariable long id, Model model) {
        model.addAttribute("id", id);
        return "reportPage";
    }
}
