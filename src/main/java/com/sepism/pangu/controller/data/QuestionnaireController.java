package com.sepism.pangu.controller.data;

import com.google.gson.Gson;
import com.sepism.pangu.model.questionnaire.Questionnaire;
import com.sepism.pangu.model.repository.QuestionnaireRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;

@Controller
@Log4j2
public class QuestionnaireController {
    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @ResponseBody
    @RequestMapping(path = "/questionnaires/{id}", method = RequestMethod.GET, produces = MediaType
            .APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public String getQuestionnaire(@PathVariable long id) {
        log.info("Getting questionnaire for {}", id);
        Questionnaire questionnaire = questionnaireRepository.findOne(id);
        return new Gson().toJson(questionnaire);
    }
}
