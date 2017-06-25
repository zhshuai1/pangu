package com.sepism.pangu.controller;

import com.google.gson.Gson;
import com.sepism.pangu.model.question.Question;
import com.sepism.pangu.model.questionnaire.Questionnaire;
import com.sepism.pangu.model.repository.QuestionRepository;
import com.sepism.pangu.model.repository.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;

@Controller
public class TestController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @ResponseBody
    @RequestMapping(path = "/testQeustion", method = RequestMethod.GET)
    public String testQuestion() {
        Question question = questionRepository.findOne(1L);
        return new Gson().toJson(question);
    }

    @ResponseBody
    @RequestMapping(path = "/testQN", method = RequestMethod.GET)
    @Transactional
    public String testQN() {
        Questionnaire questionnaire = questionnaireRepository.findOne(1L);
        return new Gson().toJson(questionnaire);
    }
}
