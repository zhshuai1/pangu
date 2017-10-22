package com.sepism.pangu.controller.data;

import com.google.gson.Gson;
import com.sepism.pangu.model.repository.QuestionnaireReportRepositoryRedis;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Log4j2
public class QuestionnaireReportController {
    private static final Gson GSON = new Gson();
    @Autowired
    private QuestionnaireReportRepositoryRedis questionnaireReportRepositoryRedis;

    @RequestMapping(path = "/reports/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getQuestionnaireReport(@PathVariable long id) {
        return GSON.toJson(questionnaireReportRepositoryRedis.findOne(id));
    }

    public String getHotQuestionnaireReports(List<Long> quesitonnaireIds) {
        return null;
    }
}
