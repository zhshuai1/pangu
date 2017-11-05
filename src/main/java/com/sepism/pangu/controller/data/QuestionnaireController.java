package com.sepism.pangu.controller.data;

import com.google.gson.Gson;
import com.sepism.pangu.model.questionnaire.Questionnaire;
import com.sepism.pangu.model.repository.QuestionnaireHotRepositoryRedis;
import com.sepism.pangu.model.repository.QuestionnaireRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Controller
@Log4j2
public class QuestionnaireController {

    private static final Gson GSON = new Gson();
    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private QuestionnaireHotRepositoryRedis questionnaireHotRepositoryRedis;

    @ResponseBody
    @RequestMapping(path = "/data/questionnaires/{id}", method = RequestMethod.GET, produces = MediaType
            .APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public String getQuestionnaire(@PathVariable long id) {
        log.info("Getting questionnaire for {}", id);
        Questionnaire questionnaire = questionnaireRepository.findOne(id);
        return new Gson().toJson(questionnaire);
    }

    @ResponseBody
    @RequestMapping(path = "/data/questionnaires/", method = RequestMethod.GET, produces = MediaType
            .APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public String getQuestionnaires(@RequestParam(required = false) Integer pageNumber,
                                    @RequestParam(required = false) Integer pageSize) {
        log.info("Getting questionnaires by start {} and page {}", pageNumber, pageSize);
        pageNumber = null == pageNumber ? 0 : pageNumber;
        pageSize = null == pageSize ? 20 : pageSize;
        pageSize = pageSize > 30 ? 30 : pageSize;
        long start = pageNumber * pageSize;
        List<Long> questionnaireIds = questionnaireHotRepositoryRedis.findIdsByRankRange(start, start + pageNumber - 1);
        if (CollectionUtils.isEmpty(questionnaireIds)) {
            return GSON.toJson(Collections.emptyList());
        }
        List<Questionnaire> questionnaires = questionnaireRepository.findByIdIn(questionnaireIds);
        return GSON.toJson(questionnaires);
    }
}
