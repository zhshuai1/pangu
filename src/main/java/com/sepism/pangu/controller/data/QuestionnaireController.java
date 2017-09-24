package com.sepism.pangu.controller.data;

import com.google.gson.Gson;
import com.sepism.pangu.model.questionnaire.Questionnaire;
import com.sepism.pangu.model.repository.QuestionnaireRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

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

    @ResponseBody
    @RequestMapping(path = "/questionnaires/", method = RequestMethod.GET, produces = MediaType
            .APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public String getQuestionnaires(@RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer size) {
        log.info("Getting questionnaires by start {} and page {}", page, size);
        page = null == page ? 0 : page;
        size = null == size ? 20 : size;
        List<Questionnaire> questionnaires = questionnaireRepository.findAllByHotGreaterThanEqualOrderByHot(0,
                new PageRequest(page, size)).getContent();
        return new Gson().toJson(questionnaires);
    }
}
