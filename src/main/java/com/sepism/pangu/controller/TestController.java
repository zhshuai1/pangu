package com.sepism.pangu.controller;

import com.google.gson.Gson;
import com.sepism.pangu.model.questionnaire.Question;
import com.sepism.pangu.model.questionnaire.Questionnaire;
import com.sepism.pangu.model.repository.QuestionRepository;
import com.sepism.pangu.model.repository.QuestionnaireRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;

@Controller
@Log4j2
public class TestController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @ResponseBody
    @RequestMapping(path = "/testQuestion", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public String testQuestion() {
        Question question = questionRepository.findOne(1L);
        return new Gson().toJson(question);
    }

    @ResponseBody
    @RequestMapping(path = "/testQN", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public String testQN() {
        Questionnaire questionnaire = questionnaireRepository.findOne(2L);
        log.info(new Gson().toJson(questionnaire));
        return new Gson().toJson(questionnaire);
    }
}
